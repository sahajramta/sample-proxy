# sample-proxy

docker build -t sahajramta/sample-broker:1.0.0

kubectl create deployment sample-proxy --image=docker.io/abhinavgarg017/office:1.9.0
kubectl expose deployment sample-proxy --type=LoadBalancer --name=sample-proxy-service --port=8080

kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: service-reader
rules:
- apiGroups: ["sample-operator.example.com"] # "" indicates the core API group
  resources: ["sampleoperators"]
  verbs: ["create, "get", "watch", "list"]


  kubectl create clusterrolebinding service-reader-pod --clusterrole=service-reader  --serviceaccount=default:default