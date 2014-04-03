package org.daybreak.emailler.domain.service;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Prey;

import java.util.List;

/**
 * Created by Alan on 14-3-25.
 */
public interface PreyService {

    public List<Prey> findPreyListByEmail(Crawler crawler, String emailAddress);

    public List<Prey> findPreyListByUrl(Crawler crawler, String fromUrl);

    public List<Prey> findVaildPreyList(Crawler crawler, boolean emailAddressValid);

    public Prey savePrey(Prey prey);

    public Prey savePrey(Prey prey, boolean force);
}
