# SmartPermission

    权限申请库，旨在简单，无依赖其他库

**使用方式**
    
    Gradle
    repositories {
        jcenter()
    }
    compile 'com.wcl.smartpermission:library:1.0'
    
    SmartPermission.build(this)
                        .addPermissions(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_CONTACTS
                        )
                        .addPermission(Manifest.permission.CAMERA)
                        .callBack(new SmartPermissionCallBack() {
                            @Override
                            public void grantEach(String permission, boolean grant) {
                                Toast.makeText(MainActivity.this, permission + " "+grant, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void grantAll(boolean grant) {
                                Toast.makeText(MainActivity.this, " 是否所有权限授权成功>> " + grant, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .request();
        
        
        #更多使用方式可查看demo
        