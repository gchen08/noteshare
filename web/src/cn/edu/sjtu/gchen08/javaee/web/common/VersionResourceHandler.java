package cn.edu.sjtu.gchen08.javaee.web.common;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;

/**
 * Created by Gong on 2018/12/24
 * 版本管理类
 */

public class VersionResourceHandler extends ResourceHandlerWrapper
{
    private ResourceHandler wrapped;

    public VersionResourceHandler(ResourceHandler wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public Resource createResource(String resourceName)
    {
        return createResource(resourceName, null, null);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName)
    {
        return createResource(resourceName, libraryName, null);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName, String contentType)
    {
        final Resource resource = super.createResource(resourceName, libraryName, contentType);

        if (resource == null)
        {
            return null;
        }

        return new ResourceWrapper()
        {

            @Override
            public String getRequestPath()
            {
                // 控制版本号
//                return super.getRequestPath() + "&v=" + Math.random(); // 开发选项，确保每次部署都更新资源
                return super.getRequestPath() + "&v=" + "1.0";
            }

            @Override // Necessary because this is missing in ResourceWrapper (will be fixed in JSF 2.2).
            public String getResourceName()
            {
                return resource.getResourceName();
            }

            @Override // Necessary because this is missing in ResourceWrapper (will be fixed in JSF 2.2).
            public String getLibraryName()
            {
                return resource.getLibraryName();
            }

            @Override // Necessary because this is missing in ResourceWrapper (will be fixed in JSF 2.2).
            public String getContentType()
            {
                return resource.getContentType();
            }

            @Override
            public Resource getWrapped()
            {
                return resource;
            }
        };
    }

    @Override
    public ResourceHandler getWrapped()
    {
        return wrapped;
    }
}
