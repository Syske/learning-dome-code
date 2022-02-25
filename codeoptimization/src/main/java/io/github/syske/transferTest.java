/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author syske
 * @version 1.0
 * @date 2021-12-02 22:12
 */
public class transferTest {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 数据初始化
        int size = 1000000;
        transfer(size);
    }

    private static void transfer(int size) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<UserEntity> userEntityList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            UserEntity userEntity = new UserEntity(123L + i, "syske", "云中志");
            userEntity.setVersion(i);
            userEntity.setVersion2(i);
            userEntity.setVersion3(i);
            userEntity.setVersion4(i);
            userEntity.setVersion5(i);
            userEntity.setVersion6(i);
            userEntity.setVersion7(i);
            userEntity.setVersion8(i);
            userEntity.setVersion9(i);
            userEntityList.add(userEntity);
        }
        System.out.printf("=======数据条数：%s========\n", size);
        long start1 = System.currentTimeMillis();
        List<UserVo> userVoList = Lists.newArrayList();
        for (UserEntity userEntity : userEntityList) {
            UserVo userVo = new UserVo();
            userVo.setId(userEntity.getId());
            userVo.setUserName(userEntity.getUserName());
            userVo.setNickName(userEntity.getNickName());
            userVo.setVersion(userEntity.getVersion());
            userVo.setVersion2(userEntity.getVersion2());
            userVo.setVersion3(userEntity.getVersion3());
            userVo.setVersion4(userEntity.getVersion4());
            userVo.setVersion5(userEntity.getVersion5());
            userVo.setVersion6(userEntity.getVersion6());
            userVo.setVersion7(userEntity.getVersion7());
            userVo.setVersion8(userEntity.getVersion8());
            userVo.setVersion9(userEntity.getVersion9());
            userVoList.add(userVo);
        }
        System.out.printf("getters/setter耗时：%s\n", System.currentTimeMillis() - start1);

        long start2 = System.currentTimeMillis();
        List<UserVo> userVoList2 = Lists.newArrayList();
        for (UserEntity userEntity : userEntityList) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userEntity, userVo);
            userVoList2.add(userVo);
        }
        System.out.printf("BeanUtils耗时：%s\n", System.currentTimeMillis() - start2);

        long start3 = System.currentTimeMillis();
        List<UserVo> userVoList3 = Lists.newArrayList();
        for (UserEntity userEntity : userEntityList) {
            UserVo userVo = new UserVo();
            BeanMap entityMap = BeanMap.create(userEntity);
            BeanMap userVOMap = BeanMap.create(userVo);
            userVOMap.putAll(entityMap);
            UserVo bean = (UserVo) userVOMap.getBean();
            userVoList3.add(bean);
//            System.out.println(bean);
        }
        System.out.printf("BeanMap耗时：%s\n", System.currentTimeMillis() - start3);

        long start4 = System.currentTimeMillis();
        List<UserVo> userVoList4 = Lists.newArrayList();
        for (UserEntity userEntity : userEntityList) {
            UserVo userVo = new UserVo();
            Class<? extends UserEntity> eClass = userEntity.getClass();
            Class<? extends UserVo> vClass = userVo.getClass();
            Field[] fields = eClass.getDeclaredFields();
            Field[] vClassDeclaredFields = vClass.getDeclaredFields();
            List<Field> fieldList =  Lists.newArrayList(vClassDeclaredFields);
            for (Field field : fields) {
                if (fieldList.contains(field)) {
                    String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method setter = vClass.getMethod("set" + name, field.getType());
                    Method getter = eClass.getMethod("get" + name, null);
                    setter.invoke(userVo, getter.invoke(userEntity, null));
                }
            }
            userVoList4.add(userVo);
        }
        System.out.printf("反射耗时：%s", System.currentTimeMillis() - start4);
    }


}
