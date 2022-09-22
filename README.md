# FedAGMusic

##Вариант 1 запуск с помощью Dockerfile (App)
Запускаем терминал в папке с приложением:
- Собираем jar архив с нашим spring webflux приложением: ```mvn clean package -Dskiptests```
- or ```mvn clean package -Dmaven.test.skip``` 
- Создать образ из нашего Dockerfile, мы должны запустить: ```docker build --tag=fedagmusic:latest .```
- Запускаем Docker контейнер из нашего образа на порту 8080: ```docker run --rm -p8080:8080 -it fedagmusic```
- Проверяем c помощью postman: ```http://localhost:8080```
- Выход из приложения и автоматическое удаление Docker контейнера: в терминале нажать "Ctrl+C"

## Вариант 2 запуск с помощью файла docker-compose.yml (Dockerfile) (App + Postgres)
- Собираем jar архив с нашим spring webflux приложением: 
- ```mvn clean package -Dskiptests``` 
- or ```mvn clean package -Dmaven.test.skip```
- Запускаем терминал и выполнить команду: ```docker-compose up```
- Проверяем c помощью postman: ```http://localhost:8080```
- Выход из приложения: в терминале нажать "Ctrl+C" 
- Удаление Docker контейнера: ```docker-compose down```

```
curl --location --request POST 'http://localhost:8080/api/v1/users/' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "anat@admin.ru",
"lastName": "Anat",
"password": "123",
"firstName": "Vas",
"role": "ROLE_ADMIN"
}'

```

Postman POST request ```http://localhost:8080/api/v1/users/```
```
{
"email": "anat@admin.ru",
"lastName": "Anat",
"password": "123",
"firstName": "Vas",
"role": "ROLE_ADMIN"
}
```

Postman POST request ```http://localhost:8080/login```
- Body/ x-www-form-urlencoded
  - KEY         /  VALUE
  - username    /  anat@admin.ru
  - password    / 123

```
curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=admin2@admin.ru' \
--data-urlencode 'password=123'
```