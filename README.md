# Baseing

![micropython: v1.15 (shields.io)](https://img.shields.io/badge/micropython-v1.15-blue) ![VOFA+](https://img.shields.io/badge/VOFA%2B-v1.3.10-green) ![APM](https://img.shields.io/apm/l/vim-mode)

## Background

在某些应用中，我们需要将`微处理器`计算或采集的相关数据分发至其它设备 (如`上位机`、`其它嵌入式设备`等)，并将数据通过上位机显示出来，这些微处理器自身往往并不具备`无线传输`功能 。对于不熟悉`无线通信`和`应用开发`的用户来说，要实现这样的功能实际上并不容易。因此，笔者提供了一种通用的解决方案，使得开发者在不需要相关基础的同时能够快速地完成`数据分发`和`数据显示`功能。整体方案如下：

![image-20220101030309794](https://s2.loli.net/2022/01/01/hipmozjkP8DMlar.png)

- 桌面上位机使用开源上位机`VOFA+`，界面美观大方、自由定制，协议高效、易用

- 移动上位机使用`Android Studio`开发，可显示波形、文字

- 服务器使用`Java`开发

- ESP32、VOFA+、Android上位机与服务器均通过`WIFI`连接，具体使用时请确保其均处于同一局域网下

- 微处理器通过`串口`将需要分发的数据传输给`ESP32`，ESP32将数据通过`TCP连接`发送至服务器，服务器保存数据后通过`TCP连接`将数据分发给`VOFA+上位机`和`Android上位机`

  

## 使用说明

1. 使用`IDEA`打开`Server`文件夹

   ![image-20220101033921484](https://s2.loli.net/2022/01/01/72vmslOkTLoA4nu.png)

2. 运行`Main`函数，启动服务器

   ![image-20220101034020448](https://s2.loli.net/2022/01/01/xB63FdrSlfDCkgn.png)

3. 运行`ClientTest`测试类，该类用于模拟`ESP32`客户端向服务器发送数据

   ![image-20220101034147637](https://s2.loli.net/2022/01/01/KBXlQf1ungvFYb2.png)

4. 连接成功后控制台会打印相关信息

   ![image-20220101034255713](https://s2.loli.net/2022/01/01/rwcjt3Fx6d2VSsv.png)

5. 启动`VOFA+`上位机，按照图示配置设置，点击连接后即可查看数据波形

   ![image-20220101034457413](https://s2.loli.net/2022/01/01/BMxZUpXketnjYFR.png)

   ![image-20220101034545689](https://s2.loli.net/2022/01/01/p6IbjzPBxiT8YuA.png)

   VOFA+的相关协议及使用方法请移步官方手册[firewater | VOFA+](https://www.vofa.plus/docs/learning/dataengines/firewater)

## License

MIT © Richard McRichface
