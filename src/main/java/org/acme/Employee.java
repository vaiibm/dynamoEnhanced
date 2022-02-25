package org.acme;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@RegisterForReflection
public class Employee {
  String name;
  Integer age;
  List<Dep> deps;

  @DynamoDbPartitionKey
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DynamoDbSortKey
  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public List<Dep> getDeps() {
    return deps;
  }

  public void setDeps(List<Dep> deps) {
    this.deps = deps;
  }

  @Override
  public String toString() {
    return "Employee [age=" + age;
    // + ", deps=" + deps.toString() + ", name=" + name + "]";
  }
}
