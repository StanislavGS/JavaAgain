<?php

class zeml {

    private $ekatte = "";

    //private $points[];
    //private $lines;
    //private $brLines=-1;
    //private $conturs[];

    public function AddRowFromFile(&$rezim, $st) {
        //echo '---'.$rezim.'--'.($rezim & 3).'<br/>';
        if (($rezim) & (1 << 2)) {
            switch ($rezim & 3) {
                case 0:  //header
                    $this->ObrabRedHeader(&$rezim, $st);
                    break;
                case 1: //layer
                    $this->DobavRedLayer(&$rezim, $st);
                    break;
                case 2: //control
                    $this->ObrabRedControl(&$rezim, $st);
                    break;
                case 3: // table
                    $this->ObrabRedTable(&$rezim, $st);
            }
        } else {
            //echo LeftWord($st);
            switch (LeftWord($st)) {
                case "HEADER": $rezim = 4;
                    break;
                case "LAYER": $rezim = 5;
                    break;
                case "CONTROL":
                    $rezim = 6;
                    break;
                case "TABLE":
                    $rezim = 7;
                    break;
            }
        }
    }

    private function DobavRedLayer(&$rezim, $st) {
        if ($st) {
            $mas = preg_split("/ /", $st, NULL, PREG_SPLIT_NO_EMPTY);
            if ((($rezim & 48) != 48) && ($mas[0] == 'L' || $mas[0] == 'C' || $mas[0] == 'P' || $mas[0] == 'S' || $mas[0] == 'T' || $mas[0] == 'END_LAYER')) {
                $rezim &= 15;
            }//close drugite raboti
            $isOpen = $rezim >> 4;
            if ($isOpen) {
                switch ($isOpen) {
                    case 1: end($this->lines)->DobavVertecs($mas, $st); //echo 'Lin---'.$brojach++;
                        break;
                    case 2: end($this->conturs)->DobavLines($mas); //echo 'Cin---'.$brojach++;
                        break;
                    case 3: end($this->texts)->DobavText($st);
                        $rezim &= 15; //echo 'Tin-'.$rezim.'-'.$isOpen.'-'.ConvertCyr($st);
                        break;
                    default:echo('Nepoznata str-ra i danni');
                }
            } else {
                switch ($mas[0]) {
                    case 'L':
                        //$ln=new Line($mas);
                        $this->lines[] = new Line($mas);
                        $rezim |= 16;
                        //echo 'L---'.$brojach++;
                        break;
                    case 'P':
                        $this->points[] = new Point($mas);
                        //echo 'P---'.$brojach++;
                        break;
                    case 'C':
                        $this->conturs[] = new Contur($mas);
                        $rezim |= 32;
                        //echo 'C-'.$rezim.'-'.$isOpen.'-'.$brojach++;
                        break;
                    case 'S':
                        $this->signs[] = new Sign($mas);
                        //echo 'S---'.$brojach++;
                        break;
                    case 'T':
                        $this->texts[] = new Text($mas);
                        $rezim |= 48;
                        //echo 'T-'.$rezim.'-'.$isOpen.'-'.$brojach++;
                        break;
                    case 'END_LAYER':$rezim = 0;
                        break;
                    //default:						
                }
            }
            /*
              if ($rezim & 0770){
              switch($rezim>>3){
              case 1:  //2ri  treti ili drug red linija
              break;
              case 2: ////2ri  treti ili drug red contur
              break
              case 3:// 2ri  red text
              }
              }else{
              if(trim($st)){
              $mas=preg_split("/ /",$st,NULL,PREG_SPLIT_NO_EMPTY);
              switch ($mas[0]){
              case 'L':
              break;
              case 'P':
              break;
              case 'C':
              break :
              case 'S':
              break;
              case 'T':
              break;
              case 'END_LAYER':
              break;
              die('Red ot layer ---'.$st.'--- kojto ne otgovarja');
              }
              }
              }
             */
        }
    }

    private function ObrabRedHeader(&$rezim, $st) {
        $mas = preg_split("/ /", trim($st), NULL, PREG_SPLIT_NO_EMPTY);
        if ($mas[0] == 'WINDOW') {
            $this->xWinLeft = (double) $mas[2];
            $this->yWinBottom = (double) $mas[1];
            $this->xWinRight = (double) $mas[4];
            $this->yWinUp = (double) $mas[3];
        }
        if (trim($st) == 'END_HEADER')
            $rezim = 0;
    }

    private function ObrabRedControl(&$rezim, $st) {
        if (trim($st) == 'END_CONTROL')
            $rezim = 0;
    }

    private function ObrabRedTable(&$rezim, $st) {
        if (trim($st) == 'END_TABLE')
            $rezim = 0;
    }

}

class Point {

    public function __construct($mas) {
        $this->type = (int) $mas[1];
        $this->num = (int) $mas[2];
        $this->x = (double) $mas[4];
        $this->y = (double) $mas[3];
        $this->h = (float) $mas[5];
        $this->klasHz = (int) $mas[6];
        $this->klasV = (int) $mas[7];
    }

}

class Line {

    //private $points[];
    public function __construct($mas) {
        $this->type = (int) $mas[1];
        $this->num = (int) $mas[2];
        $this->level = (int) $mas[3];
        if (count($mas) > 4) {
            $this->elev = (float) $mas[4];
        }
    }

    public function DobavVertecs($mas, $st) {
        $mas1 = preg_split("/;/", trim($st), NULL, PREG_SPLIT_NO_EMPTY);
        for ($i = 0; $i < count($mas1); $i++) {
            $mas2 = preg_split("/ /", $mas1[$i], NULL, PREG_SPLIT_NO_EMPTY);
            $this->points[] = new SimplePoint($mas2);
        }
    }

}

class Contur {

    //private $lines[];
    public function __construct($mas) {
        $this->type = (int) $mas[1];
        $this->num = (int) $mas[2];
        $this->x = (double) $mas[4];
        $this->y = (double) $mas[3];
    }

    public function DobavLines($mas) {
        foreach ($mas as $vle) {
            $this->lines[] = (int) $vle;
        }
    }

}

class Sign {

    public function __construct($mas) {
        $this->type = (int) $mas[1];
        $this->num = (int) $mas[2];
        $this->x = (double) $mas[4];
        $this->y = (double) $mas[3];
        $this->rot = (float) $mas[5];
        $this->scale = (float) $mas[6];
    }

}

class Text {

    public function __construct($mas) {
        $this->type = (int) $mas[1];
        $this->num = (int) $mas[2];
        $this->x = (double) $mas[4];
        $this->y = (double) $mas[3];
        if (count($mas) > 5) {
            $this->rot = (float) $mas[5];
        }
    }

    public function DobavText($st) {
        //echo $st.'<br/>';
        $mas = RazdelSKavichki($st);
        //var_dump($mas);echo '<br/>';
        if (substr($mas[0], 0, 1) == '"' xor substr($mas[0], -1, 1) == '"') {
            die(' Nekorektni kawichki v red s text' . $mas[0] . '---' . substr($mas[0], 0) . '+++' . substr($mas[0], -1) . '---');
        }
        $correction = ((substr($mas[0], 0, 1) == '"') ? 1 : 0);
        if ($correction && strlen($mas[0]) > 2) {
            $this->textString = ConvertCyr(StipQuotes($mas[0]));
        }//echo 'Zapisva string-'.strlen($this->textString).'-'.$this->textString.'<br/>';}
        if (count($mas) > 2 + $correction) {
            $this->type1 = (int) $mas[0 + $correction];
            $this->num1 = (int) $mas[1 + $correction];
            $this->grParam = $mas[2 + $correction];
            if (count($mas) > 3 + $correction && strlen($mas[3 + $correction]) > 2) {
                $this->textString .= ConvertCyr(StipQuotes($mas[3 + $correction]));
            }//echo 'Zapisva string--'.strlen($this->textString).'-'.$this->textString.'<br/>';}
        }
    }

}

class SimplePoint {

    public function __construct($mas) {
        $this->x = (double) $mas[2];
        $this->y = (double) $mas[1];
        $this->h = (float) $mas[3];
        if (count($mas) > 4) {
            $this->toch = (float) $mas[4];
        }
    }

}

function LeftWord($st) {
    if (!trim($st))
        return '';
    $st = trim($st);
    if ($pos = strpos($st, ' ')) {
        return substr($st, 0, $pos);
    } else {
        return $st;
    }
}

function RazdelSKavichki($st) {
    $i = 0;
    $isInQuote = false;
    $isBackSlash = false;
    $s2 = '';
    while ($i < strlen($st)) {
        $s1 = substr($st, $i, 1);
        if ($isInQuote) {
            $s2 .= (($s1 == ' ') ? '%s' : (($s1 == '%') ? '%%' : $s1));
        } else {
            $s2 .= $s1;
        }
        if ($s1 == '"' && !$isBackSlash) {
            $isInQuote = !$isInQuote;
        }
        $isBackSlash = ($s1 == '\\') && (!$isBackSlash);
        $i++;
    }
    //echo $st.'|||||||'.$s2.'=-=-=-=-=';
    $mas = preg_split("/ /", $s2, NULL, PREG_SPLIT_NO_EMPTY);
    foreach ($mas as &$vle) {
        if (substr($vle, 0, 1) == '"') {
            $s2 = '';
            $isPercent = false;
            for ($i = 0; $i < strlen($vle); $i++) {
                $s1 = substr($vle, $i, 1);
                if ($isPercent && ($s1 == 's')) {
                    $s2 .= ' ';
                }
                if ($isPercent && ($s1 == '%')) {
                    $s2 .= '%';
                }
                if ($isPercent && ($s1 != 's') && ($s1 != '%')) {
                    $s2 .= '%' . $s1;
                }
                if (!$isPercent && ($s1 != '%')) {
                    $s2 .= $s1;
                }
                $isPercent = (!$isPercent && ($s1 == '%'));
            }
            $vle = $s2;
            //echo $vle.'<br/>';var_dump($mas);
        }
    }
    //var_dump($mas);echo '<br/>';
    return $mas;
}

function StipQuotes($st) {
    if (strlen($st) > 0 && substr($st, 0, 1) == '"')
        $st = substr($st, 1);
    if (strlen($st) > 0 && substr($st, -1) == '"')
        $st = substr($st, 0, -1);
    return $st;
}

function ConvertCyr($ins) {
    $ou = "";
    for ($j = 0; $j < strlen($ins); $j++) {
        $c = ord($ins[$j]);
        if ($c <= 127) {
            $ou .= chr($c);
            continue;
        }
        if ($c <= 191) {
            $ou .= chr($c + 64);
            continue;
        }
        $ou .= chr($c - 64); //if ($c>191) {$ou.=chr($c-64); continue; }
    }
    //echo("</br>***".$ou."---</br>");
    return $ou;
}

?>