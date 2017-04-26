package com.andlinks.service.impl;

import com.andlinks.dao.BaseDao;
import com.andlinks.entity.BaseEntity;
import com.andlinks.service.BaseService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 王凯斌 on 2017/4/24.
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>  {

    /** 更新忽略属性 */
    private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] {
            BaseEntity.CREATE_DATE_PROPERTY_NAME,
            BaseEntity.MODIFY_DATE_PROPERTY_NAME,
            BaseEntity.VERSION_PROPERTY_NAME,
            BaseEntity.DELETED_PROPERTY_NAME };

    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public T find(Long id) {

        if (id == null) {
            return null;
        }
        return baseDao.findOne(id);
    }

    @Override
    public List<T> findAll() {

        List<T> result = new ArrayList<T>();
        baseDao.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<T> findList(Long[] ids) {

        if (ids == null) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        baseDao.findAll(Arrays.asList(ids)).forEach(result::add);
        return result;
    }

    @Override
    public Set<T> findSet(Long[] ids) {

        if (ids == null) {
            return new HashSet<T>();
        }
        Set<T> result = new HashSet<T>();
        baseDao.findAll(Arrays.asList(ids)).forEach(result::add);
        return result;
    }

    @Override
    public T save(T t) {

        return baseDao.save(t);
    }

    @Override
    public T update(T t) {

        if(!baseDao.exists(t.getId())){
            throw new IllegalArgumentException("update target does not exits");
        }
        T orginal = baseDao.findOne(t.getId());
        if (orginal != null) {
            copyProperties(t, orginal,UPDATE_IGNORE_PROPERTIES);
        }
        return baseDao.save(orginal);
    }

    @Override
    public T update(T t, String... ignore) {

        T orginal = baseDao.findOne(t.getId());
        if (orginal != null) {
            copyProperties(t, orginal, ignore);
        }
        return update(orginal);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(T... ts) {

        for(T t:ts){
            delete(t);
        }
    }

    @Override
    public void delete(T t) {

        t.setDeleted(true);
        baseDao.save(t);
    }

    @Override
    public void delete(Long id) {

        delete(find(id));
    }

    protected void copyProperties(T source, T target,
                                  String... ignoreProperties) {

        PropertyDescriptor[] propertyDescriptors = PropertyUtils
                .getPropertyDescriptors(target);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (ArrayUtils.contains(ignoreProperties, propertyName)
                    || readMethod == null || writeMethod == null) {
                continue;
            }
            try {
                Object sourceValue = readMethod.invoke(source);
                writeMethod.invoke(target, sourceValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public Page<T> findPage(Pageable pageable) {

        return baseDao.findAll(pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Long[] ids) {

        for(Long id:ids){
            delete(id);
        }
    }
}
