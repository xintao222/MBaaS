# page table
INSERT INTO page (path, page_id, layout_id, code, title, description, system, groovy, html, modified, date_created, date_modified, java_class, cms_page)
VALUES
  ('/dashboard', 'com.angkorteam.mbaas.server.page.DashboardPage', NULL,
                 'com.angkorteam.mbaas.server.page.DashboardPage',
                 'Dashboard', 'Dashboard', TRUE, NULL, NULL,
                 TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.DashboardPage', FALSE),
  ('/menu/browse', 'com.angkorteam.mbaas.server.page.menu.MenuBrowsePage', NULL,
                   'com.angkorteam.mbaas.server.page.menu.MenuBrowsePage',
                   'Menu Browse', 'Menu Browse', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menu.MenuBrowsePage', FALSE),
  ('/menu/create', 'com.angkorteam.mbaas.server.page.menu.MenuCreatePage', NULL,
                   'com.angkorteam.mbaas.server.page.menu.MenuCreatePage',
                   'Menu Create', 'Menu Create', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menu.MenuCreatePage', FALSE),
  ('/menu/modify', 'com.angkorteam.mbaas.server.page.menu.MenuModifyPage', NULL,
                   'com.angkorteam.mbaas.server.page.menu.MenuModifyPage',
                   'Menu Modify', 'Menu Modify', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menu.MenuModifyPage', FALSE),
  ('/menu/item/browse', 'com.angkorteam.mbaas.server.page.menuitem.MenuItemBrowsePage', NULL,
                        'com.angkorteam.mbaas.server.page.menuitem.MenuItemBrowsePage',
                        'MenuItem Browse', 'MenuItem Browse', TRUE, NULL, NULL,
                        TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menuitem.MenuItemBrowsePage', FALSE),
  ('/menu/item/create', 'com.angkorteam.mbaas.server.page.menuitem.MenuItemCreatePage', NULL,
                        'com.angkorteam.mbaas.server.page.menuitem.MenuItemCreatePage',
                        'MenuItem Create', 'MenuItem Create', TRUE, NULL, NULL,
                        TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menuitem.MenuItemCreatePage', FALSE),
  ('/menu/item/modify', 'com.angkorteam.mbaas.server.page.menuitem.MenuItemModifyPage', NULL,
                        'com.angkorteam.mbaas.server.page.menuitem.MenuItemModifyPage',
                        'MenuItem Modify', 'MenuItem Modify', TRUE, NULL, NULL,
                        TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.menuitem.MenuItemModifyPage', FALSE),
  ('/section/browse', 'com.angkorteam.mbaas.server.page.section.SectionBrowsePage', NULL,
                      'com.angkorteam.mbaas.server.page.section.SectionBrowsePage',
                      'Section Browse', 'Section Browse', TRUE, NULL, NULL,
                      TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.section.SectionBrowsePage', FALSE),
  ('/section/create', 'com.angkorteam.mbaas.server.page.section.SectionCreatePage', NULL,
                      'com.angkorteam.mbaas.server.page.section.SectionCreatePage',
                      'Section Create', 'Section Create', TRUE, NULL, NULL,
                      TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.section.SectionCreatePage', FALSE),
  ('/section/modify', 'com.angkorteam.mbaas.server.page.section.SectionModifyPage', NULL,
                      'com.angkorteam.mbaas.server.page.section.SectionModifyPage',
                      'Section Modify', 'Section Modify', TRUE, NULL, NULL,
                      TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.section.SectionModifyPage', FALSE),
  ('/layout/browse', 'com.angkorteam.mbaas.server.page.layout.LayoutBrowsePage', NULL,
                     'com.angkorteam.mbaas.server.page.layout.LayoutBrowsePage',
                     'Layout Browse', 'Layout Browse', TRUE, NULL, NULL,
                     TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.layout.LayoutBrowsePage', FALSE),
  ('/layout/create', 'com.angkorteam.mbaas.server.page.layout.LayoutCreatePage', NULL,
                     'com.angkorteam.mbaas.server.page.layout.LayoutCreatePage',
                     'Layout Create', 'Layout Create', TRUE, NULL, NULL,
                     TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.layout.LayoutCreatePage', FALSE),
  ('/layout/modify', 'com.angkorteam.mbaas.server.page.layout.LayoutModifyPage', NULL,
                     'com.angkorteam.mbaas.server.page.layout.LayoutModifyPage',
                     'Layout Modify', 'Layout Modify', TRUE, NULL, NULL,
                     TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.layout.LayoutModifyPage', FALSE),
  ('/page/browse', 'com.angkorteam.mbaas.server.page.page.PageModifyPage', NULL,
                   'com.angkorteam.mbaas.server.page.page.PageModifyPage',
                   'Page Modify', 'Page Modify', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.page.PageModifyPage', FALSE),
  ('/page/create', 'com.angkorteam.mbaas.server.page.page.PageCreatePage', NULL,
                   'com.angkorteam.mbaas.server.page.page.PageCreatePage',
                   'Page Create', 'Page Create', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.page.PageCreatePage', FALSE),
  ('/page/modify', 'com.angkorteam.mbaas.server.page.page.PageBrowsePage', NULL,
                   'com.angkorteam.mbaas.server.page.page.PageBrowsePage',
                   'Page Browse', 'Page Browse', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.page.PageBrowsePage', FALSE),
  ('/role/browse', 'com.angkorteam.mbaas.server.page.role.RoleModifyPage', NULL,
                   'com.angkorteam.mbaas.server.page.role.RoleModifyPage',
                   'Role Modify', 'Role Modify', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.role.RoleModifyPage', FALSE),
  ('/role/create', 'com.angkorteam.mbaas.server.page.role.RoleCreatePage', NULL,
                   'com.angkorteam.mbaas.server.page.role.RoleCreatePage',
                   'Role Create', 'Role Create', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.role.RoleCreatePage', FALSE),
  ('/role/modify', 'com.angkorteam.mbaas.server.page.role.RoleBrowsePage', NULL,
                   'com.angkorteam.mbaas.server.page.role.RoleBrowsePage',
                   'Role Browse', 'Role Browse', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.role.RoleBrowsePage', FALSE),
  ('/collection/browse', 'com.angkorteam.mbaas.server.page.collection.CollectionBrowsePage', NULL,
                         'com.angkorteam.mbaas.server.page.collection.CollectionBrowsePage',
                         'Collection Browse', 'Collection Browse', TRUE, NULL, NULL,
                         TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.collection.CollectionBrowsePage', FALSE),
  ('/collection/create', 'com.angkorteam.mbaas.server.page.collection.CollectionCreatePage', NULL,
                         'com.angkorteam.mbaas.server.page.collection.CollectionCreatePage',
                         'Collection Create', 'Collection Create', TRUE, NULL, NULL,
                         TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.collection.CollectionCreatePage', FALSE),
  ('/attribute/create', 'com.angkorteam.mbaas.server.page.attribute.AttributeCreatePage', NULL,
                        'com.angkorteam.mbaas.server.page.attribute.AttributeCreatePage',
                        'Attribute Create', 'Attribute Create', TRUE, NULL, NULL,
                        TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.attribute.AttributeCreatePage', FALSE),
  ('/attribute/browse', 'com.angkorteam.mbaas.server.page.attribute.AttributeBrowsePage', NULL,
                        'com.angkorteam.mbaas.server.page.attribute.AttributeBrowsePage',
                        'Attribute Browse', 'Attribute Browse', TRUE, NULL, NULL,
                        TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.attribute.AttributeBrowsePage', FALSE),
  ('/document/browse', 'com.angkorteam.mbaas.server.page.document.DocumentBrowsePage', NULL,
                       'com.angkorteam.mbaas.server.page.document.DocumentBrowsePage',
                       'Document Browse', 'Document Browse', TRUE, NULL, NULL,
                       TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.document.DocumentBrowsePage', FALSE),
  ('/document/create', 'com.angkorteam.mbaas.server.page.document.DocumentCreatePage', NULL,
                       'com.angkorteam.mbaas.server.page.document.DocumentCreatePage',
                       'Document Create', 'Document Create', TRUE, NULL, NULL,
                       TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.document.DocumentCreatePage', FALSE),
  ('/document/modify', 'com.angkorteam.mbaas.server.page.document.DocumentModifyPage', NULL,
                       'com.angkorteam.mbaas.server.page.document.DocumentModifyPage',
                       'Document Modify', 'Document Modify', TRUE, NULL, NULL,
                       TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.document.DocumentModifyPage', FALSE),
  ('/file/modify', 'com.angkorteam.mbaas.server.page.file.FileModifyPage', NULL,
                   'com.angkorteam.mbaas.server.page.file.FileModifyPage',
                   'File Modify', 'File Modify', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.file.FileModifyPage', FALSE),
  ('/file/create', 'com.angkorteam.mbaas.server.page.file.FileCreatePage', NULL,
                   'com.angkorteam.mbaas.server.page.file.FileCreatePage',
                   'File Create', 'File Create', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.file.FileCreatePage', FALSE),
  ('/file/browse', 'com.angkorteam.mbaas.server.page.file.FileBrowsePage', NULL,
                   'com.angkorteam.mbaas.server.page.file.FileBrowsePage',
                   'File Browse', 'File Browse', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.file.FileBrowsePage', FALSE),
  ('/user/browse', 'com.angkorteam.mbaas.server.page.user.UserBrowsePage', NULL,
                   'com.angkorteam.mbaas.server.page.user.UserBrowsePage',
                   'User Browse', 'User Browse', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.user.UserBrowsePage', FALSE),
  ('/user/create', 'com.angkorteam.mbaas.server.page.user.UserCreatePage', NULL,
                   'com.angkorteam.mbaas.server.page.user.UserCreatePage',
                   'User Create', 'User Create', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.user.UserCreatePage', FALSE),
  ('/user/modify', 'com.angkorteam.mbaas.server.page.user.UserModifyPage', NULL,
                   'com.angkorteam.mbaas.server.page.user.UserModifyPage',
                   'User Modify', 'User Modify', TRUE, NULL, NULL,
                   TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.user.UserModifyPage', FALSE),
  ('/user/password', 'com.angkorteam.mbaas.server.page.user.UserPasswordPage', NULL,
                     'com.angkorteam.mbaas.server.page.user.UserPasswordPage',
                     'User Password', 'User Password', TRUE, NULL, NULL,
                     TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.user.UserPasswordPage', FALSE),
  ('/login', 'com.angkorteam.mbaas.server.page.LoginPage', NULL,
             'com.angkorteam.mbaas.server.page.LoginPage',
             'Login', 'Login', TRUE, NULL, NULL,
             TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.LoginPage', FALSE),
  ('/logout', 'com.angkorteam.mbaas.server.page.LogoutPage', NULL,
              'com.angkorteam.mbaas.server.page.LogoutPage',
              'Logout', 'Logout', TRUE, NULL, NULL,
              TRUE, now(), now(),
   'com.angkorteam.mbaas.server.page.LogoutPage', FALSE);
