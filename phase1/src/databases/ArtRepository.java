package databases;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import entity.art.Art;
import interfaces.DataRepository;
import utils.DynamoDBConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtRepository implements DataRepository<Art> {
    private final AmazonDynamoDB clientDb;
    private final DynamoDBMapper mapper;
    private final Table table;

    public ArtRepository(DynamoDBConfig config){
        this.clientDb = config.clientDb;
        this.mapper = config.mapper;
        this.table = new DynamoDB(clientDb).getTable("art");
    }

    public Art getById(String id) {
        try{
            return new Art(table.getItem("id", id));
        } catch(Exception e){
            return null;
        }
    }

    public void delete(String id) {
        try{
            table.deleteItem("id", id);
        }
        catch(Exception e){
            System.out.println("Error deleting Art Object from AWS DynamoDB");
        }
    }

    public Art save(Art obj) {
        try{
            mapper.save(obj);
        } catch (Exception e){
            System.out.println("Error saving Art Object to AWS DynamoDB");
        }
        return obj;
    }

    public List<Art> getAll(){
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("art");

            ScanResult result = clientDb.scan(scanRequest);
            List<Art> arts = new ArrayList<>();
            for (Map<String, AttributeValue> item : result.getItems()){
                String artId = item.get("id").toString();
                artId = artId.substring(artId.indexOf(" ") + 1, artId.lastIndexOf(","));
                arts.add(getById(artId));
            }
            return arts;
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    public void deleteAll(){
        try{
            ScanRequest scanRequest = new ScanRequest()
                    .withTableName("art");

            ScanResult result = clientDb.scan(scanRequest);

            for (Map<String, AttributeValue> item : result.getItems()){
                String artId = item.get("id").toString();
                artId = artId.substring(artId.indexOf(" ") + 1, artId.lastIndexOf(","));
                delete(artId);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
