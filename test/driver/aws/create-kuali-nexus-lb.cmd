ec2-version
elb-version
as-version
elb-create-lb kuali-nexus-lb --headers --listener "lb-port=80,instance-port=80,protocol=TCP" --availability-zones "us-east-1a,us-east-1b,us-east-1c,us-east-1d"
elb-configure-healthcheck kuali-nexus-lb --headers --target "TCP:80" --interval 5 --timeout 1 --unhealthy-threshold 3 --healthy-threshold 3
as-create-launch-config kuali-nexus-lc --image-id ami-c4bf57ad --instance-type m1.small --group nexus.ks,default --key ks-key
as-create-auto-scaling-group kuali-nexus-as --launch-configuration kuali-nexus-lc --availability-zones us-east-1a,us-east-1b,us-east-1c,us-east-1d --min-size 1 --max-size 3 --load-balancers kuali-nexus-lb
as-set-desired-capacity kuali-nexus-as --desired-capacity 2
