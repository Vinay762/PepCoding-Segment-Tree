import java.io.*;
//import java.util.*;

public class RangeQueryRangeUpdate {

  public static class SegmentTree {
    int ar[], lazy[], tree[];

    SegmentTree(int arr[]) {
      ar = arr;
      tree = new int[4*ar.length];
      lazy = new int[4*ar.length];
      build(1,0,ar.length-1);
    }

    void build(int node, int start, int end){
      if(start == end){
        tree[node] = ar[start];
      }else{
        int mid= (start + end)/2;
        build(node*2,start,mid);
        build(node*2+1,mid+1,end);
        tree[node] = tree[node*2] + tree[node*2+1];
      }
    }

    void update(int l, int r, int val) {
        update(1,0,ar.length-1,l,r,val);
    }

    void update(int node, int start, int end, int l, int r, int val){
      propagate(node,start,end);
      if(end < l || start > r)return;
      if(start == end){
        tree[node] += val;
      }else if(start >= l && end <= r){
        lazy[node] += val;
        propagate(node,start,end);
      }else{
        int mid = (start + end)/2;
        update(node*2,start,mid,l,r,val);
        update(node*2+1,mid+1,end,l,r,val);
        tree[node] = tree[node*2] + tree[node*2+1];
      }
    }

    int query(int l, int r) {
      return query(1,0,ar.length-1,l,r);
    }

    private void propagate(int node, int start, int end){
      if(start == end){
        tree[node] += lazy[node];
        lazy[node] = 0;
      }else{
        tree[node] += (lazy[node])*(end-start+1);
        lazy[node*2] += lazy[node];
        lazy[node*2+1] += lazy[node];
        lazy[node] = 0;
      }
    }

    int query(int node, int start, int end, int l, int r){
      propagate(node,start,end);
      if(end < l || start > r)return 0;
      if(start == end){
        return tree[node];
      }else if(start >= l && end <= r){
        return tree[node];
      }else{
        int mid = (start + end)/2;
        int left = query(node*2,start,mid,l,r);
        int right = query(node*2+1,mid+1,end,l,r);
        return left+right;
      }
    }

  }

  public static void main(String[] args) throws Exception {
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(read.readLine());
    int arr[] = new int[n];

    for (int i = 0; i < n; i++) {
      arr[i] = Integer.parseInt(read.readLine());
    }

    SegmentTree obj = new SegmentTree(arr);

    int q = Integer.parseInt(read.readLine());

    StringBuilder out = new StringBuilder();
    while (q-- > 0) {
      String[]inp = read.readLine().split(" ");

      int t = Integer.parseInt(inp[0]);
      int l = Integer.parseInt(inp[1]);
      int r = Integer.parseInt(inp[2]);

      if (t == 0) {
        long ans = obj.query(l, r);
        out.append(ans + "\n");
      } else {
        int val = Integer.parseInt(inp[3]);
        obj.update(l, r, val);
      }
    }

    System.out.println(out);
  }

}