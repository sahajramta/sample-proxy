# sample-proxy

docker build -t sahajramta/sample-broker:1.0.0

kubectl create deployment image=docker.io/sahajramta/sample-broker:1.0.0ß
kubectl expose deployment sample-proxy --type=LoadBalancer --name=sample-proxy-service --port=8080