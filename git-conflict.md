# Git Merge Conflict

## Причина конфликта

Для демонстрации merge conflict была создана ветка
'fix/conflict-demo'.

В ветке 'fix/conflict-demo' была изменена строка описания
проекта в файле 'README.md'.

После этого та же строка была изменена другим способом
в ветке 'main'.

При выполнении команды:

###bash
git merge fix/conflict-demo
