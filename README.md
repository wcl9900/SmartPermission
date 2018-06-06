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
                Manifest.permission.READ_CONTACTS)
            .callBack(new SmartPermissionCallBack() {
                @Override
                public void grantEach(String permission, boolean grant) {
                    //单个权限申请处理回调
                }

                @Override
                public void grantAll(boolean grant) {
                    //所有权限申请处理回调
                }
            }).request();
        
        
        #更多使用方式可查看demo
        