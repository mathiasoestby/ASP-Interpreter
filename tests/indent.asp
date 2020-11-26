x = 1 # 0 indent tokens
 y = 2 # legg til 1 indent token og push findIndent() på indents-stacken
 q = 62 # Ingen forandring i indentering. Ingenting skjer (på indenteringsfronten altså).
    # Linje med kun whitespace og kommentar - ignoreres
          z = 3 # legg til 1 indent token og push findIndent() på indents-stacken
a = 0 # legg til 2 dedent tokens og pop 2 ganger på indents-stacken
                            b = 4 # legg til 1 indent token og push findIndent() på indents-stacken
                             c = 5 # legg til 1 indent token og push findIndent() på indents-stacken
                            d = 6 # Pop i en løkke til man finner indenteringsfeilen
Hei = 1
  yo = 2
