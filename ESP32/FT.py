from machine import UART
import network, usocket, time

#创建wlan对象
wl = network.WLAN(network.STA_IF)
wl.active(True)
uart = UART(2, baudrate=115200, rx=16, tx=17, timeout=10)


#服务器和ESP32应在同一个局域网下，否则无法通信
wl.connect('wifi_name', 'wifi_password')


#在WIFI连接完毕前不能连接socket
while not wl.isconnected():
    pass


#创建usocket对象并连接
s = usocket.socket()
s.connect(('server_ip', 8888))


#向服务器发送身份ID
data = s.recv(2)
while True:
    if bytes.decode(data)=='ID':
        break
    data = s.recv(2)

s.send('ESP32')

while True:
    #串口发来数据时
    if uart.any():
        sensor_msg = uart.readline()
        s.send(sensor_msg)
        print(bytes.decode(sensor_msg))