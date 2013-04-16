////////////////////////////////////////////////////////
// Portfólio: Estudo de Design Patterns e Parallax    // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ExercicioPattern
{
    public interface Observavel
    {
         void criarColecaoObservadores();

         void adicionaOuvinte(Observador novoObservador);

         void notificacao(int direcao);

         void retirarOuvinte(Observador exObservador);

         void reiniciaColecaoObservadores();
    }
}
