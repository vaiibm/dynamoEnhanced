package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;

@Path("/hello")
public class GreetingResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String hello() {
                Region region = Region.AP_SOUTH_1;
                DynamoDbClient ddb = DynamoDbClient.builder()
                                .credentialsProvider(DefaultCredentialsProvider.create())
                                .region(region)
                                .build();

                DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                                .dynamoDbClient(ddb)
                                .build();
                try {
                        // Create a DynamoDbTable object
                        DynamoDbTable<Employee> mappedTable = enhancedClient.table("Employee",
                                        StaticTableSchema.builder(Employee.class)
                                                        .newItemSupplier(Employee::new)
                                                        .addAttribute(String.class, a -> a.name("name")
                                                                        .getter(Employee::getName)
                                                                        .setter(Employee::setName)
                                                                        .tags(primaryPartitionKey()))
                                                        .addAttribute(Integer.class, a -> a.name("age")
                                                                        .getter(Employee::getAge)
                                                                        .setter(Employee::setAge)
                                                                        .tags(primarySortKey()))
                                                        // .addAttribute(String.class, a -> a.name("name")
                                                        // .getter(Customer::getName)
                                                        // .setter(Customer::setName)
                                                        // .tags(secondaryPartitionKey("customers_by_name")))
                                                        // .addAttribute(Instant.class, a -> a.name("created_date")
                                                        // .getter(Customer::getCreatedDate)
                                                        // .setter(Customer::setCreatedDate)
                                                        // .tags(secondarySortKey("customers_by_date"),
                                                        // secondarySortKey("customers_by_name")))
                                                        .build());

                        // Create a KEY object
                        // System.out.println(mappedTable.toString());
                        Key key = Key.builder()
                                        .partitionValue("vaibhav")
                                        .sortValue(22)
                                        .build();

                        // System.out.println(key.toString());
                        // Map<String, AttributeValue> key1 = new HashMap<>();
                        // key1.put("name", AttributeValue.builder().s("vaibhav").build());

                        // key1.put("age", AttributeValue.builder().n("22").build());

                        // return GetItemRequest.builder()
                        // .tableName("Employee")
                        // .key(key1)
                        // // .attributesToGet(FRUIT_NAME_COL, FRUIT_DESC_COL)
                        // .build().toString();
                        // Get the item by using the key
                        Employee result = mappedTable.getItem(key);
                        return "The email value is " + result.toString();

                } catch (DynamoDbException e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                }

                return "Hello RESTEasy";
        }
}