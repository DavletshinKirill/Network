    alter table if exists comments
       add constraint FKh4c7lvsc298whoyd4w9ta25cr
       foreign key (post_id)
       references posts;

   alter table if exists comments
      add constraint FK8omq0tc18jd43bu5tjh6jvraq
      foreign key (user_id)
      references users;

  alter table if exists posts
     add constraint FK5lidm6cqbc7u4xhqpxm898qme
     foreign key (user_id)
     references users;

 alter table if exists user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id)
    references users;