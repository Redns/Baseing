# Baseing

![micropython: v1.15 (shields.io)](https://img.shields.io/badge/micropython-v1.15-blue) ![VOFA+](https://img.shields.io/badge/VOFA%2B-v1.3.10-green) ![](https://img.shields.io/badge/Thonny-v3.3.7-orange) ![APM](https://img.shields.io/apm/l/vim-mode)

<br>
- [Baseing](#baseing)
  - [Background](#background)
  - [Environment](#environment)
  - [Usage](#usage)
      - [启动服务器](#启动服务器)
      - [连接VOFA+](#连接vofa)
      - [连接ESP32](#连接esp32)
  - [License](#license)
  
## Background

在某些应用中，我们需要将`微处理器`计算或采集的相关数据分发至其它设备 (如`上位机`、`其它嵌入式设备`等)，并将数据通过上位机显示出来，这些微处理器自身往往并不具备`无线传输`功能 。对于不熟悉`无线通信`和`应用开发`的用户来说，要实现这样的功能实际上并不容易。因此，笔者提供了一种通用的解决方案，使得开发者在不需要相关基础的同时能够快速地完成`数据分发`和`数据显示`功能。整体方案如下：

![image-20220101030309794](https://s2.loli.net/2022/01/01/hipmozjkP8DMlar.png)

- 桌面上位机使用开源上位机`VOFA+`，界面美观清新、自由定制，协议高效、易用
- 移动上位机使用`Android Studio`开发，可显示波形、文字
- 服务器使用`Java`开发
- ESP32、VOFA+、Android上位机与服务器均通过`WIFI`连接，具体使用时请确保其均处于同一局域网下
- 微处理器通过`串口`将需要分发的数据传输给`ESP32`，ESP32将数据通过`TCP连接`发送至服务器，服务器保存数据后通过`TCP连接`将数据分发给`VOFA+上位机`和`Android上位机`


<br>

## Environment

- Thonny (v3.3.7及以上)
- IDEA
- JDK9 (及以上)
- Android Studio (非必要)

<br>

## Usage

#### 启动服务器

1. 使用`IDEA`打开`Server`文件夹

   ![image-20220101033921484](https://s2.loli.net/2022/01/01/72vmslOkTLoA4nu.png)

   <br>

2. 运行`Main`类，启动服务器

   ![image-20220101034020448](https://s2.loli.net/2022/01/01/xB63FdrSlfDCkgn.png)

   <br>

3. 运行`ClientTest`测试类，该类用于模拟`ESP32`客户端向服务器发送数据

   ![image-20220101034147637](https://s2.loli.net/2022/01/01/KBXlQf1ungvFYb2.png)

   <br>

4. 连接成功后控制台会打印相关信息

   ![image-20220101034255713](https://s2.loli.net/2022/01/01/rwcjt3Fx6d2VSsv.png)

   <br>



#### 连接VOFA+

1. 启动`VOFA+`上位机，按照图示配置设置，`VOFA+`详细使用说明请参照[官方手册](https://www.vofa.plus/docs/learning)

   ![image-20220101210332265](https://s2.loli.net/2022/01/01/XxQ4aBiCEU5Ymvu.png)

   <br>

2. 点击左方`控件`界面，通过拖动控件创建界面

   ![image-20220101211921394](https://s2.loli.net/2022/01/01/spmkQEMbz73vPiS.png)

   <br>

3. 拖动波形控件并绑定相应通道

   ![image-20220101212256327](https://s2.loli.net/2022/01/01/NWXsIiVugjUzDhr.png)

   <br>

4. 点击连接后即可查看数据波形

   ![image-20220101212354301](https://s2.loli.net/2022/01/01/j1NYDBJZloS9VI7.png)

   ![image-20220101034545689](https://s2.loli.net/2022/01/01/p6IbjzPBxiT8YuA.png)

   <br>

#### 连接ESP32

1. 将微处理器的`UART串口`与ESP32板载`UART2串口`相连，两者的`TX引脚`和`RX引脚`交叉连接

   ![image-20220101212834293](https://s2.loli.net/2022/01/01/VAKvFrHGshNpZOg.png)

   <br>

2. 为`ESP32`系统板烧录固件，具体流程参见[ESP32固件烧录流程](https://krins.vercel.app/blogs/ESP32/1_Start.html)，需要烧录的固件笔者存放在项目`ESP32`文件夹下，读者也可按照教程中的方式自己下载合适的固件

   ![image-20220101213358748](https://s2.loli.net/2022/01/01/LukNhxCFeYv278w.png)

   <br>

3. ESP32相关的代码存放在项目`ESP32`文件夹下

   ![image-20220101213552415](https://s2.loli.net/2022/01/01/IBoGz46FbRKWcwe.png)

   <br>

4. 根据实际情况修改需要连接的`WIFI名称`、`WIFI密码`、`服务器IP`，服务器(电脑)需要与ESP32连接同一个WIFI

   ![image-20220101213748642](https://s2.loli.net/2022/01/01/fVm4Qqc3dDrHtuz.png)

   - `wifi_name`：需要连接的WIFI名称
   - `wifi_password`：需要连接的WIFI的密码
   - `server_ip`：服务器(电脑)的 IP 地址

   <br>

5. 如何查看服务器(电脑)的 IP 地址？

   1. 键盘按下`win+r`，在弹出的命令框中输入`cmd`

      ![image-20220101214150609](https://s2.loli.net/2022/01/01/RQDkWdC83meiEyP.png)

      <br>

   2. 命令行输入`ipconfig`并回车

      ![image-20220101214241068](https://s2.loli.net/2022/01/01/5KHmvUn7Gtsbwcp.png)

      <br>

   3. 找到`无线局域网适配器`下的`IPv4地址`，即为服务器(电脑的) IP 地址

      ![image-20220101214348994](https://s2.loli.net/2022/01/01/6XyZHSdhOnR57FD.png)

      <br>

6. 点击运行，即可运行代码`FT.py`

   ![image-20220101214551631](https://s2.loli.net/2022/01/01/MPXrWYmbKUeTsdG.png)

   <br>

## License

MIT © Richard McRichface
