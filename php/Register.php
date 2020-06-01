<?php 
    $con = mysqli_connect("localhost", "dongkeydev", "Dong230723!", "dongkeydev");
    mysqli_query($con,'SET NAMES utf8');
 
    $PID = $_POST["PID"];
    $UserPassword = $_POST["UserPassword"];
    $Email = $_POST["Email"];
    $userId = $_POST["UserId"];
 
    $statement = mysqli_prepare($con, "INSERT INTO article VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssi", $PID, $UserPassword, $UserId, $Email);
    mysqli_stmt_execute($statement);
 
 
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>
