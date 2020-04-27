package ${package};

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ${package}.${className};
<#list imports as import>
    import ${import};
</#list>

/**
* @author ${author}
* @version 1.0
* @description: TODO
* @date Create by ${author} on ${date}
*/
@RunWith(MockitoJUnitRunner.class)
public class ${className}Test {
@InjectMocks
private ${className} ${className?uncap_first};

<#list declaredFields as field>
    @Mock
    private ${field.type.simpleName} ${field.name};

</#list>
@Before
public void before() throws Exception {
}

@After
public void after() throws Exception {
}

<#list methods as method>
    /**
    * @return void
    * @description
    * @author ${author}
    * @date ${date}
    */
    @Test
    public void test${method.name?cap_first}() {

    }

</#list>
}