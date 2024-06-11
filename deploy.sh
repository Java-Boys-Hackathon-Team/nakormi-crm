#!/usr/bin/env bash

# Остановка при первой ошибке
set -e

# Проверка наличия аргумента с названием ветки
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <branch_name>"
  exit 1
fi

# Сохраняем название ветки в переменную
BRANCH_NAME=$1

# Шаг 1: Получение последних изменений из репозитория
git fetch

# Шаг 2: Переключение на основную ветку
git checkout "$BRANCH_NAME"

# Шаг 3: Слияние последних изменений
git pull origin "$BRANCH_NAME"

# Шаг 4: Остановка всех работающих контейнеров
docker-compose down

# Шаг 5: Удаление всех образов из docker-compose
docker-compose rm -f
docker image prune -f --filter "label=com.docker.compose.project=nakormi"

# Шаг 6: Сборка проекта nakormi
./gradlew -Pvaadin.productionMode=true bootJar

# Шаг 7: Запуск всех сервисов
docker-compose up -d

echo "Deployment completed successfully on branch $BRANCH_NAME"