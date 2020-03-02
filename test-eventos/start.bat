@ECHO OFF

set ARQUIVO=%~dp0eventos.json
set APLICACAO=%~dp0test-eventos-0.0.1-SNAPSHOT.jar
set JAVA=java

echo Pressione ENTER para Gravar
PAUSE >nul
start %JAVA% -jar  %APLICACAO% --gravar --arquivo=%ARQUIVO%

echo Pressione ENTER para Reproduzir
PAUSE >nul
start %JAVA% -jar %APLICACAO% --reproduzir --arquivo=%ARQUIVO%

echo Concluido com sucesso