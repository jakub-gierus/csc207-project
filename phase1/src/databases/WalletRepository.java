package databases;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import entity.art.Art;
import entity.markets.Wallet;
import interfaces.DataRepository;
import utils.DynamoDBConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WalletRepository implements DataRepository<Wallet> {
    private final AmazonDynamoDB clientDb;
    private final DynamoDBMapper mapper;
    private final Table table;

    public WalletRepository(DynamoDBConfig config){
        this.clientDb = config.clientDb;
        this.mapper = config.mapper;
        this.table = new DynamoDB(clientDb).getTable("wallet");
    }
    /**
     * Returns a Wallet if it exists in the library, null otherwise
     * @param id, id of the art to get
     * @return a Wallet object or Null
     */
    public Wallet getById(String id) {
        return new Wallet(table.getItem("id", id));
    }
    /**
     * Delete a Wallet if it exists in the library, null otherwise
     * @param id, id of the Wallet to get
     */
    public void delete(String id) {
        try{
            table.deleteItem("id", id);
        }
        catch(Exception e){
            System.out.println("Error deleting Wallet Object from AWS DynamoDB");
        }
    }
    /**
     * Saves a Wallet object into the database
     * @param obj, the art to save
     * @return the Wallet object
     */
    public Wallet save(Wallet obj) {
        try{
            mapper.save(obj);
        } catch (Exception e){
            System.out.println("Error saving Wallet object to AWS DynamoDB");
        }
        return obj;
    }
    /**
     * returns all Wallets in the databse
     * @return a list of all stored Wallets
     */
    public List<Wallet> getAll(){
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("wallet");

            ScanResult result = clientDb.scan(scanRequest);
            List<Wallet> wallets = new ArrayList<>();
            for (Map<String, AttributeValue> item : result.getItems()){
                String walletId = item.get("id").toString();
                walletId = walletId.substring(walletId.indexOf(" ") + 1, walletId.lastIndexOf(","));
                wallets.add(getById(walletId));
            }
            return wallets;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    /**
     * Delete all wallet data from the database
     */
    public void deleteAll(){
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("wallet");
            ScanResult result = clientDb.scan(scanRequest);
            for (Map<String, AttributeValue> item : result.getItems()){
                String walletId = item.get("id").toString();
                walletId = walletId.substring(walletId.indexOf(" ") + 1, walletId.lastIndexOf(","));
                delete(walletId);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
