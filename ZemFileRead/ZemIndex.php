<?php

echo <<<EOD
<form action="zem-laptop.php" method="post" enctype="multipart/form-data">
�������� �����: <input type="file"  name="file" id="file">
<input type="hidden" name="fileIsChoosen" value="19">
<input type="submit" name="submit" value="������.">
</form>
EOD;
?>