用openssl生成自签名证书
=======================================================

* 制作CA证书

  1. ca.key CA私钥
    
        openssl genrsa -des3 -out ca.key 2048

  2. 制作解密后的CA私钥（一般无此必要）

        openssl rsa -in ca.key -out ca_decrypted.key

  3. ca.crt CA根证书（公钥）

        openssl req -new -x509 -days 7305 -key ca.key -out ca.crt

* 制作生成网站的证书并用CA签名认证 

  > 在这里，假设网站域名为web.test

  1. 生成web.test证书私钥

        openssl genrsa -des3 -out web.test.pem 1024

  2. 制作解密后的web.test证书私钥

        openssl rsa -in web.test.pem -out web.test.key

  3. 生成签名请求

        openssl req -new -key web.test.pem -out web.test.csr

> 在common name中填入网站域名，如web.test即可生成改站点的证书，同时也可以使用泛域名如*.web.test来生成所有二级域名可用的网站证书。

* 用CA进行签名

    openssl ca -policy policy_anything -days 1460 -cert ca.crt -keyfile ca.key -in web.test.csr -out web.test.crt

> 其中，policy参数允许签名的CA和网站证书可以有不同的国家、地名等信息，days参数则是签名时限。如果在执行签名命令时，出现“I am unable to access the ../../CA/newcerts directory” 修改/etc/pki/tls/openssl.cnf中“dir = ./CA
 然后：

    mkdir -p CA/newcerts
    touch CA/index.txt
    touch CA/serial
    echo "01" > CA/serial

再重新执行签名命令。最后，把ca.crt的内容粘贴到web.test.crt后面。这个比较重要！因为不这样做，可能会有某些浏览器不支持。 好了，现在https需要到的网站私钥web.test.key和网站证书web.test.crt都准备完毕。接下来开始配置服务端

* 配置Nginx

  * 新开一个虚拟主机，并在server{}段中设置
  
        listen 443;
        ssl on;
        ssl_certificate /path/to/web.test.crt;
        ssl_certificate_key /path/to/web.test.key;


> 其中的路径是刚刚生成的网站证书的路径。 然后使用一下命令检测配置和重新加载nginx：

> 检测配置：

    nginx -t

> 重新加载：

    nginx -s reload

* 优化nginx配置

    > 优化nginx性能 在http{}中加入：

        ssl_session_cache shared:SSL:10m;
        ssl_session_timeout 10m;

    > 据官方文档所述，cache中的1m可以存放4000个session。 在配置https的虚拟主机server{}中加入：

        keepalive_timeout 70;

    > 有时候，会发现，在phpMyAdmin等程序登入后会错误地跳转http的问题。解决方法是定位至“location ~ .*.(php|php5)?${}”在include fcgi.conf;或者在fastcgi_param配置后面加上

        fastcgi_param HTTPS on;
        fastcgi_param HTTP_SCHEME https;