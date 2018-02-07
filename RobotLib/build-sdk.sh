#!/bin/bash
# build sdk script
# file name build-sdk.sh

# 使用说明（使用前请配置好dex2jar，gradle的环境变量）
# 1.命令行进入项目根目录
# 2.用文本编辑器打开build-sdk.sh配置里面的信息
# 3.在当前目录下执行脚本

# ---------配置信息 BEGIN----------



# ---------METHOD BEGIN------------

# 创建目录
createDir(){
	if [[ ! -d $1 ]]; then
		mkdir $1
	fi
}

# 反编译APK
decompileApk(){
	apktool d -s -f $1
}

# 生成APK
buildApk(){
	apktool b $1
}

# 解压jar
unzipJar(){
 bmbm
	jar xvf $1
}

#$1 jar name,$2 目录路径
createJar(){
	jar cvf $1 $2
}

# jar合并成dex
createDex(){ 
	echo $1
	dx --dex --output=classes.dex $1
}

# 签名
apkSigner(){
	jarsigner -verbose -keystore $1 -storepass $3 -signedjar $4 $5 $2 -digestalg SHA1 -sigalg MD5withRSA
}

# 安装
installApk(){
	adb install -r $1
}

# ---------METHOD END------------


# ----------CMD-----------

# 编译lib_master，生成混淆的Jar
echo "编译robotlib..."
createDir build/release
gradle proguard
echo "编译robotlib成功"


