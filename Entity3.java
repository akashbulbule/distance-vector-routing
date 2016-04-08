public class Entity3 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
    	for(int i=0;i<NetworkSimulator.NUMENTITIES;i++){
    		for(int j =0;j<NetworkSimulator.NUMENTITIES;j++){
    	distanceTable[i][j]=999;
    	
    		}
    	}
    	distanceTable[0][0]=7;
    	distanceTable[1][1]=999;
    	distanceTable[2][2]=2;
    	distanceTable[3][3]=0;
    	
    	int[] arr= new int[4];
    	arr[0]=distanceTable[0][0];
    	arr[1]=distanceTable[1][1];
    	arr[2]=distanceTable[2][2];
    	arr[3]=distanceTable[3][3];
    	System.out.println("Initial table at node 3:");
    	printDT();
    	System.out.print("\nsending out DV:");
    	for (int i=0;i<arr.length;i++){
    		System.out.print(arr[i] + " ");
    	}
    	System.out.println("\n\n");
    	Packet p30 = new Packet(3,0,arr);
    	Packet p32 = new Packet(3,2,arr);
    	
        NetworkSimulator.toLayer2(p30);
        NetworkSimulator.toLayer2(p32);
        
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	int[] neighbors={0,1,2};
    	int[] minlist = new int[4];
    	int src=p.getSource();
    	
    	boolean update=false;
    	int[] update1 = new int[4];
    	for(int i=0;i<neighbors.length;i++){
    		if (distanceTable[neighbors[i]][src]>NetworkSimulator.cost[3][src] + p.getMincost(neighbors[i])){
    			distanceTable[neighbors[i]][src]=NetworkSimulator.cost[3][src] + p.getMincost(neighbors[i]);
    			update1[neighbors[i]]=distanceTable[neighbors[i]][src];
    			
    	}
    	}	
    		int min=0;
    		for(int k=0;k<NetworkSimulator.NUMENTITIES;k++){
    			min=999;
    			for (int m=0;m<NetworkSimulator.NUMENTITIES;m++){
    			if (min>distanceTable[k][m]){
    				min=distanceTable[k][m];
    			}
    			minlist[k]=min;
    			
    		  }
    			if (minlist[k]!=0 && update1[k]==minlist[k]){
        			update=true;
        		}
    		}
    		
    		
    		
    		
    		
    	
    	minlist[3]=0;
    	if (update){
    		System.out.println("Node 3 table updated:");
    		printDT();
    		System.out.print("\nsending out DV:");
    		for (int i=0;i<minlist.length;i++){
        		System.out.print(minlist[i] + " ");
        	}
    		System.out.println("\n\n");
    		Packet p3_0 = new Packet(3,0,minlist);
        	Packet p3_2 = new Packet(3,2,minlist);
        	NetworkSimulator.toLayer2(p3_0);
            NetworkSimulator.toLayer2(p3_2);
            
    	}
    	else{
    		System.out.println("Node 3 table not updated");
    		printDT();
    		System.out.println("\n\n");
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
               
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}