-- Удаление существующего внешнего ключа
ALTER TABLE comments
DROP CONSTRAINT comments_task_id_fkey;

-- Добавление нового внешнего ключа с каскадным удалением
ALTER TABLE comments
ADD CONSTRAINT fk_comments_tasks
FOREIGN KEY (task_id) REFERENCES tasks (id)
ON DELETE CASCADE;