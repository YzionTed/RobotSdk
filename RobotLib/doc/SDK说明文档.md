## 比亦特机器人SDK接入文档

### 步骤1：将jar加入到libs

#### Android Studio在工程中的gradle配置

    dependencies {
        compile files('libs/robotlib.jar')
        compile files('libs/serialport.jar')
    }
    
把so库文件放到jniLibs目录下

### 步骤2：修改AndroidManifest.xml文件

#### 配置权限声明

	<permission android:name="android.permission.SERIAL_PORT"/>

### 步骤3：在APP中添加接口代码

#### 在Application中初始化SDK

初始化SDK，应该放在Application的onCreate()方法里，只需要初始化一次

调用接口：

    RobotClient.init(Context);
    参数说明：
    Context：表示上下文Context

示例代码：

    //在Application的onCreate()方法里
    @Override
    public void onCreate() {
        super.onCreate();
        RobotClient.init(this);
    }

主Activity的onCreate()方法里添加初始化代码

调用接口：

	RobotClient.getInstance().onCreate();

示例代码：

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    RobotClient.getInstance().onCreate();
	}
	
主Activity的onDestroy()方法里添加销毁代码    
调用接口：

	RobotClient.getInstance().onDestroy();

示例代码：
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    RobotClient.getInstance().onDestroy();
	}

#### 向前运动

机器人向前运动，执行stop()可以停止运动

调用接口：

    RobotClient.getInstance().forward();

#### 向后运动

机器人向后运动，执行stop()可以停止运动

调用接口：
	
	RobotClient.getInstance().backward();

#### 向左转动

机器人原地左转，执行stop()可以停止运动

调用接口：

	RobotClient.getInstance().turnLeft();
   
#### 向右转动

机器人原地右转，执行stop()可以停止运动

调用接口：

	RobotClient.getInstance().turnRight();
   
#### 停止运动/退出模式

如果机器人当前正在运动，调用则停止当前运动，如果机器人进入某种模式，调用则退出当前模式

调用接口：

	RobotClient.getInstance().stop();
	   
#### 关闭投影

机器人打开投影仪，打开过程需要几秒时间

调用接口：

	RobotClient.getInstance().openSerialPort();
   
#### 关闭投影

机器人关闭投影仪

调用接口：

	RobotClient.getInstance().closeSerialPort();

#### 自动回去充电

机器人自动寻找附近的充电桩自动回冲，执行stop()可以停止运动

调用接口：

	RobotClient.getInstance().gotoChargePower();
	
#### 旋转

机器人根据所给角度原地旋转，执行stop()可以停止运动

参数说明：

degree：角度，取值[0-360]

调用接口：

	RobotClient.getInstance().rotate(int degree);
	
#### 游荡模式

机器人随意走动，遇到障碍物自动避开，执行stop()可以停止运动

调用接口：

	RobotClient.getInstance().randomWalk();
	
#### 省电模式

机器人进入省电模式，关闭轮子驱动，执行stop()可以退出省电模式

调用接口：

	RobotClient.getInstance().powerSaveMode();