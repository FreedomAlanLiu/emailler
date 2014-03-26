package org.daybreak.emailler.domain.service;

import org.daybreak.emailler.domain.model.Crawler;
import org.daybreak.emailler.domain.model.Ware;

import java.util.List;

/**
 * Created by Alan on 14-3-25.
 */
public interface WareService {

    public List<Ware> findWareList(Crawler crawler, String url);

    public Ware saveWare(Ware ware);

    public List<Ware> saveWareList(List<Ware> wareList);

    public Ware pollWare(Crawler crawler);
}
