% === Hechos de inventario ===
tengo(catalizador_mineral, 2).
tengo(h, 4).
tengo(o, 2).
tengo(f, 3).
tengo(e, 5).
tengo(a, 4).
tengo(c, 2).
tengo(n, 3).
tengo(s, 1).
tengo(p, 2).
tengo(k, 1).
tengo(na, 2).
tengo(cl, 3).
tengo(fe, 4).
tengo(cu, 2).
tengo(ag, 1).

% === Reglas de recetas ===
receta(agua, [h,o], [2,1]).
receta(agua, [h,o], [1,1]).
receta(dioxido_carbono, [c,o], [1,2]).
receta(sal, [na,cl], [1,1]).
receta(amoniaco, [n,h], [1,3]).
receta(sulfurico, [h,s,o], [2,1,4]).
receta(etanol, [c,h,o], [2,6,1]).
receta(metano, [c,h,o], [1,4,1]).
receta(oxido_aluminio, [al,o], [2,3]).
receta(acero, [fe,c], [1,1]).
receta(clorhidrico, [h,cl], [1,1]).
receta(lejia, [na,cl,o], [1,1,1]).
receta(perclorico, [h,cl,o], [1,1,4]).
receta(sulf_hierro, [fe,h,s,o], [1,2,1,4]).
receta(acido_carbonico, [agua,dioxido_carbono], [1,1]).
receta(agua_salada, [agua,sal], [1,1]).
receta(bicarbonato, [acido_carbonico,na], [1,1]).
receta(sulfato_cobre, [sulfurico,cu], [1,1]).
receta(nitrato_potasio, [clorhidrico,k], [1,1]).
receta(acetato_sodio, [etanol,na], [1,1]).
receta(sulfato_hierro, [sulfurico,fe], [1,1]).
receta(cloruro_plata, [clorhidrico,ag], [1,1]).
receta(agua_oxigenada, [h,o], [2,2]).
receta(solucion_limpieza, [lejia,agua], [1,1]).
receta(combustible_avanzado, [c,h,o], [1,4,2]).
receta(acido_sulfurico_concentrado, [sulfurico,agua_oxigenada], [1,1]).
receta(solucion_electrolitica, [agua_salada,lejia], [1,1]).
receta(mezcla_explosiva, [combustible_avanzado,agua_oxigenada], [1,1]).
receta(solucion_desinfectante, [solucion_limpieza,clorhidrico], [1,1]).
receta(acido_super_concentrado, [acido_sulfurico_concentrado,perclorico], [1,1]).
receta(solucion_ultra_limpieza, [solucion_desinfectante,solucion_limpieza], [1,1]).
receta(explosivo_ultimo_nivel, [mezcla_explosiva,acido_super_concentrado], [1,1]).
receta(acido_carbonico, [agua], [1]).
receta(acido_radioactivo, [plutonio,agua], [1,1]).
receta(plutonio, [sulfato_hierro,acido_super_concentrado], [1,1]).

% === Reglas de crafteo ===
calcular_requerimientos_totales(Ings, Cants, Requerimientos) :-
	calcular_requerimientos_lista(Ings, Cants, RequerimientosParciales),
	agrupar_requerimientos(RequerimientosParciales, Requerimientos).
calcular_requerimientos_lista([], [], []).
calcular_requerimientos_lista([Ing|Ings], [Cant|Cants], Requerimientos) :-
	( receta(Ing, IngsReceta, CantsReceta) -> 
	multiplicar_cantidades(IngsReceta, CantsReceta, Cant, IngsExpandidos, CantsExpandidos),
	calcular_requerimientos_lista(IngsExpandidos, CantsExpandidos, ReqExpandidos),
	calcular_requerimientos_lista(Ings, Cants, ReqResto),
	append(ReqExpandidos, ReqResto, Requerimientos)
	;
	calcular_requerimientos_lista(Ings, Cants, ReqResto),
	Requerimientos = [(Ing, Cant)|ReqResto]
).
agrupar_requerimientos([], []).
agrupar_requerimientos([(Ing, Cant)|Resto], [(Ing, Total)|Agrupados]) :-
sumar_requerimientos(Ing, Resto, Cant, Total, RestoFiltrado),
agrupar_requerimientos(RestoFiltrado, Agrupados).
sumar_requerimientos(_, [], Acum, Acum, []).
sumar_requerimientos(Ing, [(Ing, Cant)|Resto], Acum, Total, RestoFiltrado) :-
	Acum1 is Acum + Cant,
	sumar_requerimientos(Ing, Resto, Acum1, Total, RestoFiltrado).
sumar_requerimientos(Ing, [(Otro, Cant)|Resto], Acum, Acum, [(Otro, Cant)|RestoFiltrado]) :-
	Ing \= Otro,
	sumar_requerimientos(Ing, Resto, Acum, Acum, RestoFiltrado).
puedo_crear_con_recursos(Ings, Cants) :-
calcular_requerimientos_totales(Ings, Cants, Requerimientos),
verificar_reqs_con_inventario(Requerimientos).
puedo_crear(Prod) :-
    receta(Prod, Ings, Cants),
    puedo_crear_con_recursos(Ings, Cants).
verificar_reqs_con_inventario([]).
verificar_reqs_con_inventario([(Ing, Cant)|T]) :-
    tengo(Ing, CantInv),
    CantInv >= Cant,
    verificar_reqs_con_inventario(T).
multiplicar_cantidades([], [], _, [], []).
multiplicar_cantidades([Ing|Ings], [Cant|Cants], Factor, [Ing|IngsResult],[CantMult|CantsResult]) :- 
	 CantMult is Cant * Factor,
	multiplicar_cantidades(Ings, Cants, Factor, IngsResult, CantsResult).

