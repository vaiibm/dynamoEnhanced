package org.acme;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@RegisterForReflection
public class Dep {
String name;
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public Integer getAge() {
    return age;
}
public void setAge(Integer age) {
    this.age = age;
}
Integer age;
@Override
public String toString() {
    return "updated Dep [age=" + age + ", name=" + name + "]";
}
}