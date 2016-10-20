<#include "/java_copyright.include"/>
package ${basepackage}.common;

import cn.starteasy.core.common.domain.BaseDomain;
import cn.starteasy.core.common.protocol.RequestT;
import cn.starteasy.core.common.restful.ITypeReference;
import cn.starteasy.core.common.restful.RawTypeReference;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 支持泛型，这里把所有的 request的泛型参数在这里做记录    （因为spring requestbody处理会丢掉泛型信息）
 * <p/>
 * 创建时间: 14/11/30 下午7:53<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class TypeReferenceMaps implements ITypeReference {

    private Map<String, TypeReference> typeReferenceMaps = Maps.newHashMap();
    public void init(){
        //typeReferenceMaps.put("/admin/mubs/role/test", new TypeReference<RequestT<BaseDomain>>(){});
        //typeReferenceMaps.put("/user/login", RawTypeReference.stringTypeReference);

        //TODO add here
    }

    @Override
    public TypeReference getTypeReference(String url){
        return typeReferenceMaps.get(url);
    }
}