package cn.chenzecheng.smartrmmonolith.infracore.aggregate;

import cn.chenzecheng.smartrmmonolith.infracore.api.CommonError;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEvent;
import cn.chenzecheng.smartrmmonolith.infracore.event.DomainEventBus;
import cn.chenzecheng.smartrmmonolith.infracore.exception.DomainException;

/**
 * @author: yoda
 * @description:
 */
public abstract class AggregateBase {

    protected long version;
    protected DomainEventBus eventBus;
    private boolean versionInc = false;

    protected void incVersion() {
        version++;
        versionInc = true;
    }

    public long getVersion() {
        return version;
    }

    public boolean isVersionInc() {
        return versionInc;
    }

    protected void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    protected void emitEvent(DomainEvent event) {
        if (eventBus != null) {
            eventBus.post(event);
        } else {
            throw new DomainException(CommonError.NoEventBus);
        }
    }


}
