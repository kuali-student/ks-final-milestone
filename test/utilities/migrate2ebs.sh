#!/bin/sh

vol_size=$1
desc=$2
mount_dir=${3:-/mnt}
dev=${4:-/dev/sdp}

# Check required opts
if [ $# -lt 2 ]
then
	echo "Error: Must specify at least the required opts volume_size and description..."
	echo "Syntax: $0 volume_size description [mount_dir dev]"
	exit
fi

# Check for valid values
case $vol_size in
	[0-9]* )
		;;
	* )
		echo "Vol_size must be numeric"
		exit
		;;
esac

echo "vol_size: $vol_size"
echo "desc: $desc"
echo "mount_dir: $mount_dir"
echo "dev: $dev"


# Call the environment setup script
. ~/.aws/aws.sh

# Get basic info from instance meta-data
echo "Getting basic info from instance meta-data..."
instance_id=`curl -s http://169.254.169.254/latest/meta-data/instance-id`
avail_zone=`curl -s http://169.254.169.254/latest/meta-data/placement/availability-zone`


# Create the Volume
echo "Creating volume..."
vol=`ec2-create-volume -K "$EC2_PRIVATE_KEY" -C "$EC2_CERT" -z "$avail_zone" --size $vol_size| cut -f2`

# Waiting for volume to become available
echo "Waiting for volume to become available..."
while [[ "$vol_status" != "available"  ]];
do
	vol_status=`ec2-describe-volumes -K "$EC2_PRIVATE_KEY" -C "$EC2_CERT" "$vol" | awk '{print $5}'`
	echo Status of "$vol" : $vol_status
done

# Attach the volume
echo "Attaching volume..."
ec2attvol "$vol" -K "$EC2_PRIVATE_KEY" -C "$EC2_CERT" -i "$instance_id" -d "$dev"
while [[ "$vol_status" != "attached"  ]];
do
	vol_status=`ec2-describe-volumes -K "$EC2_PRIVATE_KEY" -C "$EC2_CERT" "$vol" | grep ATTACHMENT | cut -f5`
	echo Status of "$vol" : $vol_status
done


# Prepare the volume
echo "Preparing volume..."

mkfs.ext3 "$dev"
mkdir -p /vol
mount "$dev" /vol
rm -rf $mount_dir/image*
rm -rf $mount_dir/img-mnt
mkdir -m 000 $mount_dir/img-mnt


# Use bundle to create a clean image (we will not upload)
echo "Using bundle to create a clean image (we will not upload)..."

ec2-bundle-vol -c $EC2_CERT -k $EC2_PRIVATE_KEY -u $AMAZON_USER_ID -e /vol,$mount_dir -d $mount_dir


# take the clean image and install on the EBS Volume
echo "Taking the clean image and install on the EBS Volume..."
echo "mount -o loop $mount_dir/image $mount_dir/img-mnt"

mount -o loop $mount_dir/image $mount_dir/img-mnt
rsync -av $mount_dir/img-mnt/ /vol/


# Set the fstab up 
echo "Setting up the fstab up..."

cat > /vol/etc/fstab << FSTABEOF
# <file system> <mountpoint> <type> <options> <dump> <pass>
proc               /proc           proc    defaults        0       0
/dev/sda3          None            swap    defaults        0       0
/dev/sdb           /               ext3    defaults        0       0
/dev/sda2          /mnt            ext3    defaults        0       0
FSTABEOF


# Snapshot the volume. Note the snapshot id for the registration step
echo "Taking snapshot of the volume..."
echo "ec2addsnap -C $EC2_CERT -K $EC2_PRIVATE_KEY -d $desc $vol"
umount /vol
ec2addsnap -C $EC2_CERT -K $EC2_PRIVATE_KEY -d "$desc" $vol
umount $mount_dir/image