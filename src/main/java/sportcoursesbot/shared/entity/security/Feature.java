package sportcoursesbot.shared.entity.security;

public enum Feature {
    //admin
    VIEW_USERS, EDIT_SIMPLE_USER, BLOCK_SIMPLE_USER, CREATE_NEW_COURSE, DELETE_COURSE, EDIT_COURSE,

    //super admin
    GIVE_ADMIN_PERMISSION,
}