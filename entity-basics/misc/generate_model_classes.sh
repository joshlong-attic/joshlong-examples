guaranteeDirectory(){ mkdir -p $1; }
mvn clean install  ;
python update_model_classes.py ;
export d="../src/main/java/org/springsource/examples/crm/model/"
guaranteeDirectory "$d"
cp -rd target/hibernate3/generated-sources/* ../src/main/java/
