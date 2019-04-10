<?php

// 创建进程
// 进程对应的执行函数
// function doProcess(swoole_process $worker){
//     echo "PID,$worker->pid \n";
//     sleep(3);
// }

// // 创建子进程
// for($i = 0;$i < 3;$i++){
//     $process = new swoole_process("doProcess");
//     $pid = $process->start();
// }

// // 回收结束运行的子进程
// swoole_process::wait();



// 进程事件
$workers = [];   // 进程数组
$worker_num = 3;    // 创建进程的数据量


// 创建启动进程
for($i = 0;$i < $worker_num;$i++){
    $process = new swoole_process("doProcess");    // 创建单独的新进程
    $pid = $process->start();   // 启动进程，并获取进程ID
    $workers[$pid] = $process;  // 存入进程数组
}
