package covid.tracing.common.datatype;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class DatatypeService {

    public void countMutableIntMap(Map<String, MutableInt> mutableIntMap, String key) {
        MutableInt count = mutableIntMap.get(key);
        if(count == null) {
            mutableIntMap.put(key, new MutableInt());
        } else {
            count.increment();
        }
    }

    public HashMap<String, Integer> convertToHashMap(Map<String, MutableInt> src) {
        HashMap<String, Integer> dest = new HashMap<String, Integer>();
        Iterator<String> keys = src.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            dest.put(key, src.get(key).get());
        }
        return dest;
    }
}
