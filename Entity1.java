public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity1()
    {
    	for(int i=0;i<NetworkSimulator.NUMENTITIES;i++){
    		for(int j =0;j<NetworkSimulator.NUMENTITIES;j++){
    	distanceTable[i][j]=999;
    	
    		}
    	}
    	distanceTable[0][0]=1;
    	distanceTable[1][1]=0;
    	distanceTable[2][2]=1;
    	distanceTable[3][3]=999;
    	
    	int[] arr= new int[4];
    	arr[0]=distanceTable[0][0];
    	arr[1]=distanceTable[1][1];
    	arr[2]=distanceTable[2][2];
    	arr[3]=distanceTable[3][3];
    	System.out.println("\nInitial table at node 1:");
    	printDT();
    	System.out.print("sending out DV:");
    	for (int i=0;i<arr.length;i++){
    		System.out.print(arr[i] + " ");
    	}
    	System.out.println("\n\n");
    	Packet p10 = new Packet(1,0,arr);
    	Packet p12 = new Packet(1,2,arr);
    	
        NetworkSimulator.toLayer2(p10);
        NetworkSimulator.toLayer2(p12);
        
        
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	int[] neighbors={0,2,3};
    	int[] minlist = new int[4];
    	int src=p.getSource();
    	int[] update1 = new int[4];
    	boolean update=false;
    	for(int i=0;i<neighbors.length;i++){
    		if (distanceTable[neighbors[i]][src]>NetworkSimulator.cost[1][src] + p.getMincost(neighbors[i])){
    			distanceTable[neighbors[i]][src]=NetworkSimulator.cost[1][src] + p.getMincost(neighbors[i]);
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
    		
    		
    		
    		
    		
    	
    	minlist[1]=0;

    	if (update){
    		System.out.println("Node 1 table updated:");
    		printDT();
    		System.out.print("\nsending out DV:");
    		for (int i=0;i<minlist.length;i++){
        		System.out.print(minlist[i] + " ");
        	}
    		System.out.println("\n\n");
    		Packet p1_0 = new Packet(1,0,minlist);
        	Packet p1_2 = new Packet(1,2,minlist);
        	NetworkSimulator.toLayer2(p1_0);
            NetworkSimulator.toLayer2(p1_2);
            
    	}
    	else{
    		System.out.println("Node 1 table not updated");
    		printDT();
    		System.out.println("\n\n");
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 1)
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