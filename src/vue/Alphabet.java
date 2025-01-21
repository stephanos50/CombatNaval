/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;
/**
 *
 * @author stefanos
 */
public enum Alphabet {
    A("un"), 
    B("deux"), 
    C("trois"),
    D("quatre"), 
    E("cinq"), 
    F("six"), 
    G("sept"), 
    H("huit"), 
    I("neuf"), 
    J("dix"),
    K("onze"),
    L("douze"),
    M("treize"),
    N("quatorze"),
    O("quinze"),
    P("seize"),
    Q("dixsept"),
    R("dixhuit"),
    S("dixneuf"),
    T("vingt"),
    U("vingtun"),
    V("vingtdeux"),
    W("vingttrois"),
    X("vingtquatre"),
    Y("vingtcinq"),
    Z("vingtsix");
    
    private final String string;
    
    Alphabet(String string){
        this.string = string;
    }
    
    @Override
    public String toString() {
        return string;
    }
}
