# Smart Social Hackaton 2024 🚀🐶🐈

## Кейс №3

### Система управления волонтерской деятельностью "Накорми CRM"

### Внимание!


**Система уже развернута в интернете и вы можете с ней ознакомиться без локальной установки**

- Испытать в браузере: [https://nakormi-crm.ru/login](https://nakormi-crm.ru/login)
- Испытать телеграм-бота: [https://t.me/NakormiProjectBot](https://t.me/NakormiProjectBot)
- Для получения доступа обратитесь к разработчикам!

### Команда разработки:
- Рустам Курамшин - @KuramshinRustam
- Александр Янчий - @AlexYanchiy
- Рустам Зулкарниев - @WerderR
- Владислав Калинин - @First_Kalinin
- Рустам Гулямов - @gulyamovrustam

### Запуск проекта

- Клонируйте репозиторий с GitHub

- Скачайте и установите JDK 17 для своей ОС на сайте Eclipse [по этой ссылке](https://adoptium.net/temurin/releases/)

- Выполните команду сборки:

```shell
# linux or mac os
./gradlew -Pvaadin.productionMode=true bootJar

# windows 
gradlew.bat -Pvaadin.productionMode=true bootJar
```

- Установите Docker Desktop с [официального сайта](https://www.docker.com/products/docker-desktop/)

- Запустите все контейнеры docker-compose следующей командой:
```shell
docker-compose up -d
```

- Чтобы войти в систему "Накорми CRM" перейдите по ссылке [http://localhost:8080](http://localhost:8080). Логин: "super_admin", пароль: "admin".