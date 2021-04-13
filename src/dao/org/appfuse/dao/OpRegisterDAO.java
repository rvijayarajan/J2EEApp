package org.appfuse.dao;

import java.util.List;
import org.appfuse.model.OpRegister;


public interface OpRegisterDAO extends DAO {

    public OpRegister getOpRegister(Long id);

    public List getOpRegisters(OpRegister opregister);

    public void saveOpRegister(final OpRegister opregister);

    public void removeOpRegister(final Long id);
}
