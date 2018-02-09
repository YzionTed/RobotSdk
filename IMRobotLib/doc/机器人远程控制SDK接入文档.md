## 比亦特机器人远程控制SDK接入文档

特别注意：因为imrobotlib.jar需要依赖网易云信sdk(nim-basesdk-4.7.3.jar和云信的so库文件)，所以主项目需要先按照网易云信SDK文档接入云信后，才能接入该SDK进行调试，不然编译不会通过

### 机器人端接入说明

### 步骤1：将jar加入到libs

#### Android Studio在工程中的gradle配置

    dependencies {
        compile files('libs/imrobotlib.jar')
    }
    
注意：imrobotlib.jar需要依赖网易云信sdk(nim-basesdk-4.7.3.jar和云信的so库文件)

### 步骤2：修改AndroidManifest.xml文件

#### 配置权限声明

	<uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

### 步骤3：在APP中添加接口代码

1.初始化SDK，应该放在Application的onCreate()方法里，只需要初始化一次

调用接口：

    IMRobotClient.init(context);
    
2.在接收云信消息的方法里加入代码 IMRobotClient.handleCommand(messages);用于接收和解析消息体中的命令

调用接口：

	IMRobotClient.handleCommand(messages)
	返回值：boolean类型，true表示收到命令并执行，false表示没有收到命令

示例
	
	//在云信接收消息的回调方法里接入处理命令的代码
	Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {
                    boolean isHandle = IMRobotClient.handleCommand(messages);
                    //isHandle返回true表示收到命令，false表示没有收到命令
                }
            };

3.设置目标云信ID IMRobotClient.setTargetAccid(accid)
这里的accid为手机端的云信ID

调用接口：

	IMRobotClient.setTargetAccid(accid)

---

### 手机端接入说明

### 步骤1：将jar加入到libs

#### Android Studio在工程中的gradle配置

    dependencies {
        compile files('libs/imrobotlib.jar')
    }
    
注意：imrobotlib.jar需要依赖网易云信sdk(nim-basesdk-4.7.3.jar和云信的so库文件)

### 步骤2：修改AndroidManifest.xml文件

#### 配置权限声明

	<uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

### 步骤3：在APP中添加接口代码

1.初始化SDK，应该放在Application的onCreate()方法里，只需要初始化一次

调用接口：

    IMRobotClient.init(context);

2.设置目标云信ID IMRobotClient.setTargetAccid(accid)
这里的accid为机器人端的云信ID

调用接口：

	IMRobotClient.setTargetAccid(accid)
	
#### 向前运动

机器人向前运动，执行stop()可以停止运动

调用接口：

    IMRobotClient.forward();

#### 向后运动

机器人向后运动，执行stop()可以停止运动

调用接口：
	
	IMRobotClient.backward();

#### 向左转动

机器人原地左转，执行stop()可以停止运动

调用接口：

	IMRobotClient.turnLeft();
   
#### 向右转动

机器人原地右转，执行stop()可以停止运动

调用接口：

	IMRobotClient.turnRight();
   
#### 停止运动/退出模式

如果机器人当前正在运动，调用则停止当前运动，如果机器人进入某种模式，调用则退出当前模式

调用接口：

	IMRobotClient.stop();
	   
#### 打开投影

机器人打开投影仪，打开过程需要几秒时间

调用接口：

	IMRobotClient.openProjector();
   
#### 关闭投影

机器人关闭投影仪

调用接口：

	IMRobotClient.closeProjector();

#### 自动回去充电

机器人自动寻找附近的充电桩自动回冲，充电桩距离机器人太远会无法识别，执行stop()可以停止运动

调用接口：

	IMRobotClient.gotoChargePower();
	
#### 原地旋转

机器人根据所给角度原地旋转，执行stop()可以停止运动

参数说明：

degree：角度必须大于0并且小于360，取值(0-360)

调用接口：

	IMRobotClient.rotate(int degree);
	
#### 游荡模式

机器人随意走动，遇到障碍实物会自动避开，执行stop()可以停止运动

调用接口：

	IMRobotClient.randomWalk();
	
#### 省电模式

机器人进入省电模式，关闭轮子驱动，执行stop()可以退出省电模式

调用接口：

	IMRobotClient.powerSaveMode();
	
---
	
### 保留SDK的接口不被混淆

混淆配置

	-keep class com.bit.imrobotlib.IMRobotClient {*;}