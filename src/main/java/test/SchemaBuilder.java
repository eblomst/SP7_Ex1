/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.persistence.Persistence;

/**
 *
 * @author emmablomsterberg
 */
public class SchemaBuilder {
    public static void main(String[] args) {
        Persistence.generateSchema("lam_seedMaven_war_1.0-SNAPSHOTPU", null);
    }
}
