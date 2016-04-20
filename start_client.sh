#launch the RMI client with the security policy included in the file rmiserver_policy

java -jar -Xmx1024m -Djava.security.policy=rmiserver_policy StreamSimRMIClientRemote.jar $1 $2