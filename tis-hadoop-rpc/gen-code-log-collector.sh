protoc --plugin=protoc-gen-grpc-java=/Users/mozhenghua/Downloads/protoc-gen-grpc-java \
  --grpc-java_out="./src/main/java"   --java_out="./src/main/java" --proto_path="./" ./log-collector.proto ./common-msg.proto