<?php

header('Content-Type: text/html');

session_start();
include_once "classZemCad.php";
include_once "ClassDisplay.php";
/*
  include_once "zem-laptop-data.php";
  echo(count($inp)).'<br/>';
  $rezim=0;
  //startDB($lnk);
  $cl=new zeml;
  for($j=0;$j<count($inp);$j++){
  //echo($inp[$j].'<br/>');
  $cl->AddRowFromFile($rezim,$inp[$j]);
  //if ($rezim>0){
  //	add_row_to_tables(&$rezim,$inp[$j],$lnk);
  //}else{
  //	check_for_rezim(&$rezim,$inp[$j],$lnk);
  //}

  }
 */

//echo '<br/><br/><br/> session---';var_dump($_SESSION);
//echo '<br/><br/><br/> file---';var_dump($_FILES);
//echo '<br/><br/><br/> !isset ($_SESSION[zem])---';echo !isset ($_SESSION['zem']);
//echo '<br/><br/><br/> isset($_FILES[file])---';echo isset($_FILES['file']);echo '<br/><br/><br/>';

$wi = 800;
$hei = 500;
$_SESSION['wi'] = $wi;
$_SESSION['hei'] = $hei;

if (isset($_FILES['file'])) {//if (!isset ($_SESSION['zem']) && isset($_FILES['file'])){ 
    $cl = new zeml;
    if ($_FILES["file"]["error"] > 0) {
        echo "Error: " . $_FILES["file"]["error"] . "<br>";
        $err = true;
    } else {
//echo "Upload: " . $_FILES["file"]["name"] . "<br>";
//echo "Type: " . $_FILES["file"]["type"] . "<br>";
//echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
//echo "Stored in: " . $_FILES["file"]["tmp_name"]. "<br>";
//echo "Temporary name:" . $_FILES['file']['tmp_name']. "<br>";
//echo "<br/><br/><br/><br/>";
        $handle = @fopen($_FILES['file']['tmp_name'], "r");
        if ($handle) {
            while (!feof($handle)) {
                if (($buffer = fgets($handle, 4096)) !== false) {
                    $st2 = trim($buffer);
                    $cl->AddRowFromFile($rezim, $st2);
                } else {
                    echo('Gre6ka pri chetene na red');
                }
            }
        } else {
            echo('Gre6ka pri otvarjane na fajla');
        }
    }



//echo 'points'.count($cl->points).'<br/>';
//echo 'lines'.count($cl->lines).'<br/>';
//echo 'conturs'.count($cl->conturs).'<br/>';
//echo 'signs'.count($cl->signs).'<br/>';
//echo 'texts'.count($cl->texts).'<br/>';
    $_SESSION['zem'] = serialize($cl);
    $currWin = array($cl->xWinLeft, $cl->yWinBottom, $cl->xWinRight, $cl->yWinUp);
    if (($wi / $hei) > (($currWin[2] - $currWin[0]) / ($currWin[3] - $currWin[1]))) {
        $correk = ($currWin[3] - $currWin[1]) * $wi / $hei - ($currWin[2] - $currWin[0]);
        $currWin[0] -= 0.5 * $correk;
        $currWin[2] += 0.5 * $correk;
    } else {
        $correk = ($currWin[2] - $currWin[0]) * $hei / $wi - ($currWin[3] - $currWin[1]);
        $currWin[1] -= 0.5 * $correk;
        $currWin[3] += 0.5 * $correk;
    }
    $_SESSION['curWin'] = serialize($currWin);
} else {
    $cl = unserialize($_SESSION['zem']);
    $currWin = unserialize($_SESSION['curWin']);
}
//var_dump($cl);
//endDB($lnk);

/*
  Rezimi
  parvi 2 bita
  00-izvan block li dugo
  01-layer
  10-control
  11-table
  sledvashti 3 bita
  000-nishoto o6te ne  zapocnato
  001-HEADER
  002-
  0- izvan block e
  1-Layer cadaster
  11-Layer cadaster i linija zapochnata
  12-Layer cadaster i contur zapochnat
 */


if (isset($_GET['action']) && ($_SESSION['timeold'] < time())) {
    switch ($_GET['action']) {
        case 0:
            $tmp = 0.5 * ($currWin[2] - $currWin[0]);
            $currWin[0] -= $tmp;
            $currWin[2] -= $tmp;
            break;
        case 1:
            $tmp = 0.5 * ($currWin[3] - $currWin[1]);
            $currWin[1] += $tmp;
            $currWin[3] += $tmp;
            break;
        case 2:
            $tmp = 0.5 * ($currWin[2] - $currWin[0]);
            $currWin[0] += $tmp;
            $currWin[2] += $tmp;
            break;
        case 3:
            $tmp = 0.5 * ($currWin[3] - $currWin[1]);
            $currWin[1] -= $tmp;
            $currWin[3] -= $tmp;
            break;
        case 4:
            $tmp = 0.25 * ($currWin[2] - $currWin[0]);
            $tmp1 = 0.25 * ($currWin[3] - $currWin[1]);
            $currWin[0] += $tmp;
            $currWin[1] += $tmp1;
            $currWin[2] -= $tmp;
            $currWin[3] -= $tmp1;
            break;
        case 5:
            $tmp = 0.25 * ($currWin[2] - $currWin[0]);
            $tmp1 = 0.25 * ($currWin[3] - $currWin[1]);
            $currWin[0] -= $tmp;
            $currWin[1] -= $tmp1;
            $currWin[2] += $tmp;
            $currWin[3] += $tmp1;
            break;
    }
    $_SESSION['curWin'] = serialize($currWin);
}

$dspl = new Display($currWin[0], $currWin[1], $currWin[2], $currWin[3]);

foreach ($cl->lines as $lin) {
    $isFirst = true;
    foreach ($lin->points as $poi) {
        if (!$isFirst) {
            $dspl->DobavLine($poiold->x, $poiold->y, $poi->x, $poi->y);
        } else {
            $isFirst = false;
        }
        $poiold = $poi;
    }
}

foreach ($cl->texts as $tx) {
    $dspl->DobavText($tx->x, $tx->y, $tx->textString);
}
$_SESSION['display'] = serialize($dspl);

echo '<img src="ImageZem.php?' . SID . '" alt="PROBAIMAGE-1" style="padding-left: 2cm;padding-top: 2.5cm;">';
//echo '<a href="ImageZem.php?'.SID.'">Tagadak</a> ';

$_SESSION['timeold'] = time();
echo '<Table style="width:25%">';
echo '<tr>';
echo '	<td with="50px"><a href="' . $_SERVER['PHP_SELF'] . '?' . SID . '&action=0">Left</a></td>';
echo '	<td><a href="' . $_SERVER['PHP_SELF'] . '?action=1">Up</a></td>';
echo '	<td><a href="' . $_SERVER['PHP_SELF'] . '?action=2">Right</a></td>';
echo '	<td><a href="' . $_SERVER['PHP_SELF'] . '?action=3">Down</a></td>';
echo '	<td><a href="' . $_SERVER['PHP_SELF'] . '?action=4">+</a></td>';
echo '	<td><a href="' . $_SERVER['PHP_SELF'] . '?action=5">-</a></td>';
echo '	<td><a href="SaveTo.php?' . SID . '&action=6">Save to .jpg</a></td>';
echo '	<td><a href="SaveTo.php?' . SID . '&action=7">Save to .dxf</a></td>';
echo '	<td><a href="ZemIndex.php">New .zem</a></td>';
echo '</tr>';
echo '</Table>';

//echo time().'  and  '.microtime(true);
//var_dump($dspl);
/*
  echo '<Table style="padding-left: 2cm;width:25%">';
  echo '	<tr><th>'.$currWin[0].'</th></tr>';
  echo '	<tr><th>'.$currWin[1].'</th></tr>';
  echo '	<tr><th>'.$currWin[2].'</th></tr>';
  echo '	<tr><th>'.$currWin[3].'</th></tr>';
  echo '</Table>';
 */
?>