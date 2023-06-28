insert into users
        (enabled, main_photo, password, username)
    values
        (true, 'https://material.angular.io/assets/img/examples/shiba2.jpg', '123456', 'user1');

        insert
            into
                user_roles
                (role, username)
            values
                ('STUDENT', 'user1');

                update
                        user_roles
                    set
                        user_id=1
                    where
                        id=1;

insert into users
        (enabled, main_photo, password, username)
    values
        (true, 'https://material.angular.io/assets/img/examples/shiba2.jpg', '123456', 'user2');

        insert
            into
                user_roles
                (role, username)
            values
                ('STUDENT', 'user2');

                update
                        user_roles
                    set
                        user_id=2
                    where
                        id=2;


insert into users
        (enabled, main_photo, password, username)
    values
        (true, 'https://material.angular.io/assets/img/examples/shiba2.jpg', '1234', 'admin');

        insert
            into
                user_roles
                (role, username)
            values
                ('SUPER_USER', 'admin');

                update
                        user_roles
                    set
                        user_id=3
                    where
                        id=3;


