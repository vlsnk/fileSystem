API для виртуальной файловой системы

Пакет com.filesystem содержит интерфейсы для работы с файловой системой.
Пакет com.filesystem.implementation содержит реализацию этих интерфейсов.

VFS - интерфейс файловой системы.
VirtualFileSystemImpl - реализация, синглтон, содержит:
 - AccessManager - управляет пользователями/группами доступа
 - корневую папку root
 - поиск файла по имени
 - удаление пользователя/группы доступа
 
Access - интерфейс управления пользователями/группами доступа.
AccessManager - реализация, синглтон.

FileAccess - интерфейс управления доступа к файлу (hasAccess, setAccess, removeAccess).

VirtualFileImpl - реализация интерфейсов VirtualFile/VirtualDirectory.
