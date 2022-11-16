package com.lixingyong.common.autoconfigure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.util.Assert;

/**
 * @author LIlGG
 * @since 2022-11-12
 */
public class AutoConfigurationEntry {
    private final String name;

    private final String className;

    private AutoConfigurationEntry parent;

    @NotNull
    private List<AutoConfigurationEntry> child;

    private boolean isDefault = false;

    public AutoConfigurationEntry(String className) {
        this(className, null);
    }

    public AutoConfigurationEntry(String className,String name) {
        Assert.notNull(className, "ClassName 不允许为空");
        this.name = name;
        this.className = className;
        this.child = new ArrayList<>();
        this.isDefault = false;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public AutoConfigurationEntry getParent() {
        return parent;
    }

    @NotNull
    public List<AutoConfigurationEntry> getChild() {
        return child;
    }

    public void setAutoConfigurationChild(Collection<String> configurations) {
        this.child = configurations.stream()
            .map(AutoConfigurationEntry::new)
            .collect(Collectors.toList());
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void addAutoConfigurationChild(String configuration) {
        this.child.add(new AutoConfigurationEntry(configuration));
    }

    public void setParent(
        AutoConfigurationEntry parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AutoConfigurationEntry) {
            AutoConfigurationEntry
                that = (AutoConfigurationEntry) o;
            return  Objects.equals(className, that.className);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
